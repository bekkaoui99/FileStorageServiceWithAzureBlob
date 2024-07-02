package com.learning.filestorageazureblob.configuration;

import com.azure.storage.blob.BlobClientBuilder;
import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.BlobServiceClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class AzureBlob {

    @Value("${azure.storage.account-name}")
    private String accountName;

    @Value("${azure.storage.sas-token}")
    private String sasToken;

    @Value("${azure.storage.connection-string}")
    private String connectionString;

    @Value("${azure.storage.container-name}")
    private String containerName;

    @Bean
    public BlobServiceClient blobServiceClient() {
        return new BlobServiceClientBuilder()
                .endpoint("https://" + accountName + ".blob.core.windows.net")
                .sasToken(sasToken)
                .buildClient();
    }

    @Bean
    public BlobClientBuilder blobClientBuilder() {
        return new BlobClientBuilder()
                .connectionString(connectionString)
                .containerName(containerName);
    }
}
