package cn.valinaa.auction.utils;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * @author Valinaa
 * 
 * @Description:
 * @Date: 2023-07-02 23:49
 */
public class MFileUtil {


    public static String sacePicFile(MultipartFile multipartFile){
        String  path = System.getProperty("user.dir") + "\\src\\main\\resources\\static\\imgs\\goods";
        File fileDir = new File(path);
        if(!fileDir.exists()) {
            fileDir.mkdirs();
        }
        String xd = UUID.randomUUID() + ".jpg";
        path = path+"\\"+ xd;
        try {
            multipartFile.transferTo(new File(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return xd;
    }


}
