package com.example.springaichatmodel.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ImageDetailsDTO {
    private String similarImagesLink;
    private String imageDetails;
    private int imageSize;
}
