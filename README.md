# Lab9 Spring Boot Transaction

REST API ระบบฝากเงินอย่างง่าย พัฒนาด้วย Spring Boot + JPA + PostgreSQL

## ความสามารถ
- สร้างบัญชีธนาคาร
- ดูข้อมูลบัญชี
- ฝากเงินเข้าบัญชี
- บันทึกประวัติการฝากเงิน
- ใช้ `@Transactional` เพื่อให้การเพิ่มยอดเงินและบันทึกประวัติทำงานเป็น Transaction เดียวกัน
- ถ้าขั้นตอนใดล้มเหลว ระบบจะ Rollback ทั้งหมด

## Run
แก้ `src/main/resources/application.properties` ให้ตรงกับ PostgreSQL ของเครื่อง แล้วรัน

```bash
mvn spring-boot:run
```

## API

### Create account
POST `/api/accounts`

```json
{
  "accountNumber": "ACC001",
  "accountName": "Chatdanay",
  "initialBalance": 1000.00
}
```

### Deposit
POST `/api/accounts/1/deposit`

```json
{
  "amount": 500.00,
  "simulateFailure": false
}
```

### Test rollback
POST `/api/accounts/1/deposit`

```json
{
  "amount": 500.00,
  "simulateFailure": true
}
```

เมื่อ `simulateFailure=true` จะเกิด exception หลังเพิ่มยอดเงิน แต่ก่อนบันทึกประวัติ ดังนั้น `@Transactional` จะ Rollback และยอดเงินจะไม่เปลี่ยน

### Get account
GET `/api/accounts/1`

### Get deposit history
GET `/api/accounts/1/deposits`
