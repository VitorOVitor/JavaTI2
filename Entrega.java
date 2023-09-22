import com.microsoft.azure.cognitiveservices.vision.faceapi.*;
import com.microsoft.azure.cognitiveservices.vision.faceapi.models.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public class Entrega {


    private static final String RECOGNITION_MODEL3 = RecognitionModel.RECOGNITION_03;

    public static FaceClient authenticate(String endpoint, String key) {
        return FaceClient.builder()
                .endpoint(endpoint)
                .credential(new AzureKeyCredential(key))
                .buildClient();
    }

    private static CompletableFuture<List<DetectedFace>> detectFaceRecognize(FaceClient faceClient, String url, String recognitionModel) {
    
        return faceClient.face()
                .detectWithUrlAsync(url, new DetectFaceOptions().recognitionModel(recognitionModel).detectionModel(DetectionModel.DETECTION_02))
                .thenApply(detectedFaces -> {
                    System.out.println(detectedFaces.size() + " face(s) detectada(s) na imagem `" + new File(url).getName() + "`");
                    return detectedFaces;
                });
    }

    public static CompletableFuture<Void> findSimilar(FaceClient client, String recognitionModel) {
        System.out.println("========Achar Similares========\n");

        Scanner scanner = new Scanner(System.in);

        System.out.println("Insira o link da face base (links muito grandes podem causar erros):\n");
        String sourceImageFileName = scanner.nextLine();

        List<String> targetImageFileNames = new ArrayList<>();
        String aux;
        int i = 1;

        do {
            System.out.println("Insira o link das possíveis faces similares e digite FIM quando acabar (links muito grandes podem causar erros):\n");
            aux = scanner.nextLine();
            if (!aux.equals("FIM")) {
                targetImageFileNames.add(aux);
            }
            i++;
        } while (!aux.equals("FIM"));

        List<UUID> targetFaceIds = new ArrayList<>();
        for (String targetImageFileName : targetImageFileNames) {

            List<DetectedFace> faces = detectFaceRecognize(client, targetImageFileName, recognitionModel).join();
       
            targetFaceIds.add(faces.get(0).faceId());
        }


        List<DetectedFace> detectedFaces = detectFaceRecognize(client, sourceImageFileName, recognitionModel).join();
        System.out.println();

   
        List<SimilarFace> similarResults = client.face()
                .findSimilar(detectedFaces.get(0).faceId(), null, null, targetFaceIds)
                .stream()
                .toList();

        i = 1;
        for (SimilarFace similarResult : similarResults) {
            System.out.println("A imagem " + i + " com o FaceID:" + similarResult.faceId() + " é similar à imagem base com a confiança: " + similarResult.confidence() + ".");
            i++;
        }
        System.out.println();
        return CompletableFuture.completedFuture(null);
    }

    public static void main(String[] args) {
 
        Scanner scanner = new Scanner(System.in);
        System.out.println("Insira a URL da sua aplicação no Azure:\n");
        String urlServico = scanner.nextLine();
        System.out.println("Insira a chave da sua aplicação no Azure:\n");
        String chaveServico = scanner.nextLine();

      
        FaceClient client = authenticate(urlServico, chaveServico);
        findSimilar(client, RECOGNITION_MODEL3).join();
    }
}
