package cn.valinaa.auction.utils;

import org.apache.commons.lang3.StringUtils;
import cn.valinaa.auction.mapper.GoodsMapper;
//import com.sun.deploy.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * @author Valinaa
 * 
 * @Description:
 * @Date: 2023-07-09 14:47
 */
public class DealOld implements Runnable{

    private GoodsMapper goodsMapper;
    private Integer g;

    public DealOld(GoodsMapper goodsMapper){
        this.goodsMapper = goodsMapper;
        g = 5;
    }

    @Override
    public void run() {
        System.out.println(g);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String nowTime = formatter.format(LocalDateTime.now());
        List<Integer> idList = goodsMapper.getOldId(nowTime);
        // todo 不确定正确
        String[] ss = StringUtils.split(idList.toString(), "[]");
        String ids = "-1";
        if(ss.length != 0){
            ids += "," + ss[0];
        }
        Integer t = goodsMapper.updateOldGoods(ids);
    }
}
