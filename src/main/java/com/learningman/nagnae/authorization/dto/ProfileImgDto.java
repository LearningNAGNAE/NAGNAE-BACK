package com.learningman.nagnae.authorization.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProfileImgDto {
	private int challengeNo;
    private String saveName;
    private String orgName;
    private String filePath;
    private long fileSize;
}
