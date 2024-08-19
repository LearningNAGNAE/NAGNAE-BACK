package com.learningman.nagnae.authorization.model;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProfileImgVo {
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
