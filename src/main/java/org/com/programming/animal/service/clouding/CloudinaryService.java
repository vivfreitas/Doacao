package org.com.programming.animal.service.clouding;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;

@Service
public class CloudinaryService {
    private final Cloudinary cloudinary;

    // Essa parte vai estabelecer a conexão entre a minha conta no Cloudnary.
    // É necessário colocar os dados lá no nosso application.properties
    public CloudinaryService(
            @Value("${cloudinary.cloud_name}") String cloudName,
            @Value("${cloudinary.api_key}") String apiKey,
            @Value("${cloudinary.api_secret}") String apiSecret) {

        cloudinary = new Cloudinary(ObjectUtils.asMap(
                "cloud_name", cloudName,
                "api_key", apiKey,
                "api_secret", apiSecret,
                "secure", true // Usa URLs https
        ));
    }

    /*
    * O que faz? Este é o metodo que faz o trabalho de verdade. Ele recebe um MultipartFile
    * (que é o arquivo da imagem que o front-end enviou). file.getBytes(): Converte o arquivo de imagem
    * em um fluxo de dados brutos (bytes), que é o que pode ser enviado pela internet. cloudinary.uploader().upload(...):
    * Este é o comando principal. É o nosso "carteiro" pegando os bytes da imagem e enviando para a nuvem do Cloudinary.
    * */
    public String uploadFile(MultipartFile file){
        try{
            Map uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.asMap(
                    "public_id", UUID.randomUUID().toString()
            ));
            return uploadResult.get("secure_url").toString();
        }catch (IOException e){
            throw new RuntimeException("Falha ao fazer o upload do ficheiro para o Cloudinary.");
        }
    }
}
