package com.learningman.nagnae.authorization.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProfileImgDto {
	private int fileno;
	private int categoryno;
    private String savename;
    private String orgname;
    private String filepath;
    private int insertuserno;
    private String insertdate;
    private int modifyuserno;
    private String modifydate;
}
