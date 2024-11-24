package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.demo.model.UserInfo;
import com.example.demo.model.UserInfoRepository;

import jakarta.servlet.http.HttpSession;

@Controller
public class MemberCenterController {
	
	@Autowired
	private UserInfoRepository userInfoRepository;

	// 顯示個人會員中心頁面 (http://localhost:8080/memberCenter)
    @GetMapping("/memberCenter")
    public String showmemberCenter() {
        return "memberCenter/memberCenter";
    }
    
    // 顯示個人資料頁面，從資料庫讀取 userId 對應的資料
    @GetMapping("/memberCenter/profile")
    public String showMemberProfile(Model model, HttpSession session) {
        // 從 Session 中獲取 userId
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null) {
            throw new RuntimeException("User is not logged in");
        }

        // 查詢資料庫，獲取 userId 對應的資料
        UserInfo userInfo = userInfoRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));

        // 將數據傳遞到模板頁面
        model.addAttribute("userInfo", userInfo);
        return "memberCenter/memberProfile";
    }
    
    // 更新會員資料 API
    @PutMapping("/profile/update")
    public ResponseEntity<String> updateMemberProfile(@RequestBody UserInfo userInfo, HttpSession session) {
        // 從 Session 獲取 userId
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null) {
            throw new RuntimeException("User is not logged in");
        }

        // 查詢資料庫，確認用戶是否存在
        UserInfo existingUser = userInfoRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // 更新資料
        existingUser.setUserName(userInfo.getUserName());
        existingUser.setUserPassword(userInfo.getUserPassword());
        existingUser.setUserTel(userInfo.getUserTel());

        // 儲存更新後的資料
        userInfoRepository.save(existingUser);

        return ResponseEntity.ok("Profile updated successfully");
    }
    
    
    // 顯示購買清單頁面 (http://localhost:8080/memberCenter/purchase)
    @GetMapping("/memberCenter/purchase")
    public String showmemberPurchase() {
        return "memberCenter/memberPurchase";
    }
    
    // 顯示訂單通知頁面 (http://localhost:8080/memberCenter/order)
    @GetMapping("/memberCenter/order")
    public String showmemberOrder() {
        return "memberCenter/memberOrder";
    }
    
    // 顯示個人地址頁面 (http://localhost:8080/memberCenter/address)
    @GetMapping("/memberCenter/address")
    public String showmemberAddress() {
        return "memberCenter/memberAddress";
    }
    
    // 顯示個人地址頁面 (http://localhost:8080/memberCenter/payment)
    @GetMapping("/memberCenter/payment")
    public String showmemberPayment() {
        return "memberCenter/memberPayment";
    }

}
