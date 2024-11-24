# 20241124_CHKWGameStop

//更新
1. homepage.html 
=>更新首頁最新商品的讀取功能 (固定顯示最新15項商品/5秒刷新一次)

3. itemDisplay.html
=> 更新商品展示頁面的讀取功能 (商品名稱/上架時間/價錢)

5. itemSearch.html
=> 搜尋功能邏輯想不太到

6. memberProfile.html
=> 更新會員資料(讀取session裡的userId/ username/ userPassword/ userTel做顯示)的更新功能

7. HomePageController
@GetMapping("/itemDisplay/{id}") 透過商品id讀取商品資料欄位
@GetMapping("/items/latest15") 查詢最新15項的商品資料

9. MemberCenterController
@GetMapping("/memberCenter/profile")
@PutMapping("/profile/update")
=> 皆從session獲取userId

//新增
7. ad.html
8. Ad.java
9. AdRepository.java
10. AdController.java
=> ad MVC的RCU功能正常 (bug: 更新完成後圖片會顯示不出來)
=> ad用在商城首頁的動態廣告牆
