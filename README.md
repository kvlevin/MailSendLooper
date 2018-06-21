# MailSendLooper
轮流发送邮件


## 使用方法
### 在sender.txt 设置发送者邮箱,按照以下格式
```
example@example.com   password  smtp.server.com
example1@example.com   password  smtp.server.com
```


### 在target.txt 设置接收者邮箱，按照以下格式
```
receiver@example.com
receiver1@example.com

```

### 在目录下 保存一个.eml格式的文件

##说明
* data.db采用sqlite 数据库 其中有相应的数据结构
* dao 采用mybatis框架