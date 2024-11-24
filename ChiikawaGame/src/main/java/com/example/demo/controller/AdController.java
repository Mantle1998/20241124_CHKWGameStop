package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.model.Ad;
import com.example.demo.model.AdRepository;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Base64;
import java.util.stream.Collectors;

@Controller
public class AdController {

    @Autowired
    private AdRepository adRepository;

    // 顯示廣告資料總覽頁面 (http://localhost:8080/adInfo)
    @GetMapping("/adInfo")
    public String showAdInfo() {
        return "ad/ad";
    }

    // 取得所有廣告資料，回傳 JSON 格式 (http://localhost:8080/adInfo/json)
    @GetMapping("/adInfo/json")
    @ResponseBody
    public List<Map<String, Object>> getAdInfoJson() {
        return adRepository.findAll().stream().map(ad -> {
            Map<String, Object> adMap = new HashMap<>();
            adMap.put("adId", ad.getAdId());
            adMap.put("adName", ad.getAdName());
            adMap.put("adInfo", ad.getAdInfo());
            adMap.put("adImageBase64", ad.getAdImage() != null 
                ? Base64.getEncoder().encodeToString(ad.getAdImage()) 
                : null);
            return adMap;
        }).collect(Collectors.toList());
    }

    // 新增廣告資料 (http://localhost:8080/adInfo/add)
    @PostMapping("/adInfo/add")
    @ResponseBody
    public ResponseEntity<String> addAdInfo(
        @RequestParam("adName") String adName,
        @RequestParam("adInfo") String adInfo,
        @RequestParam("adImage") MultipartFile adImage) {
        try {
            Ad ad = new Ad();
            ad.setAdName(adName);
            ad.setAdInfo(adInfo);

            // 將圖片文件轉換為二進位數據
            if (!adImage.isEmpty()) {
                ad.setAdImage(adImage.getBytes());
            }

            adRepository.save(ad);
            return ResponseEntity.ok("新增成功");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("新增失敗：" + e.getMessage());
        }
    }

    // 更新廣告資料 (http://localhost:8080/adInfo/update)
    @PostMapping("/adInfo/update")
    @ResponseBody
    public ResponseEntity<String> updateAdInfo(@RequestBody Ad updatedAd) {
        try {
            // 根據 ID 查找現有的廣告資料
            Ad ad = adRepository.findById(updatedAd.getAdId())
                    .orElseThrow(() -> new RuntimeException("廣告資料不存在"));

            // 更新廣告資料欄位
            ad.setAdName(updatedAd.getAdName());
            ad.setAdImage(updatedAd.getAdImage());
            ad.setAdInfo(updatedAd.getAdInfo());

            // 儲存更新後的廣告資料
            adRepository.save(ad);
            return ResponseEntity.ok("更新成功");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("更新失敗：" + e.getMessage());
        }
    }
}