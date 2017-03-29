# 理赔助手--API
---
* 登录
  ---
  * xxxxx/SpringDemo/servlets/login/LoginServlet
  * Method: POST
  * request: userName + password
  * response: Result<User>
* 注册
  ----
  * xxxxx/SpringDemo/servlets/login/RegisterServlet
  * Method: POST
  * request: User
  * response: Result<String>
* 理赔计算
  ---
  * xxxxx/SpringDemo/serlvets/record/RecordAddServlet
  * Method: POST
  * request:Record + Location
  * response: Result<RecordRes>
  * 备注：
    1. 上传的Record 附带：location_id + location + result_id
    2. 计算完毕后，生成RecordRes，并保存在数据库，同时返回
* 理赔记录
  ---
  * xxxxx/SpringDemo/servlets/record/RecordQueryServlet
  * Method: POST
  * request:String + Integer(第一个元素Record_id + 当前请求页数page)
  * response: Result<List<Record>>
* 获取记录结果详情
  ---
  * xxxxx/SpringDemo/servlets/result/ResultQueryServlet
  * Method: POST
  * request:String(Result_id)
  * response: Result<RecordRes>
* 修改用户信息
  ---
  * xxxxx/SpringDemo/Servlets/user/UserServlet
  * Method: POST
  * request: String + Object (User_id 和 想要修改的内容)
  * 备注："想要修改的内容"事先需要咱Client端判断是否重复
    1. String: equals
    2. int: ==
* 上传用户图片
  ---
  * xxxxx/SpringDemo/servlets/upload/UploadServlet
  * Method: POST
  * request: String + File(用户id + 图片)
* 请求得到某地区的城市/农村 XXX收入XXX标准【未决策】
  ---
