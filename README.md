This is The Implementation of ATM-Emulator

for starting project please follow the steps:

* in postgres
  * make a database in Postgres named "BANK-DB" 

  * To have initial data, Run the file "data.sql" in postgres

* in application
  * go to bankservice\src\main\resources 

  * open application-dev.properties file 
	
  * update the postgres url, username, and password 

* start both atmservice and bankservice microservices (default active profile is 'dev')

* to see APIs
  * swagger
    * open http://localhost:8040/atm-service/api/v1/swagger-ui.html to use swagger ui
  * postman
    * open postman and import the postmancollection that is in the root 
    
	
	there are three main APIs 

	1- CardVerification check cardNumber validity (for all needed Authorizations please add "Basic YXRtOmF0bQ==" to Authorization field)

	--wrong cardNumber
	{
 		"cardNumber" : 1122323 
	}

	--correct cardNumber
	{
		"cardNumber" : 6280231451904303
	}

	--not active cardNumber
	{
		"cardNumber" : 628023145234765
	}

	--expired cardNumber
	{
		"cardNumber" : 628023145412347
	}


	2- CarPinVerification checks inserted pin validity'
	--correct pin for carNo 6280231451904303
 	{
		 "pin" : 1233
 	}

	--incorrect pin for carNo 6280231451904303
	{
	"pin" : 1234
	}
	** after 3 times of entering wrong pin the card will be deactive


	3- BalanceManagement you can check balance, deposit, withdraw  or get receipt 
	{
		"requestType":"CHECK_BALANCE"
	}

	{
		"requestType":"DEPOSIT",
		"description":"deposit 200",
		"amount":200
	}

	{
		"requestType":"WITHDRAW",
		"description":"withdraw 300",
		"amount":300
	}

	{
		"requestType":"WITHDRAW",
		"description":"withdraw 1300",
		"amount":1300
	}
	
	
	{
		"requestType":"GET_RECEIPT",
		"fromDate":"2021-11-27",
		"toDate":"2021-11-28"
	}



* there are 3 more APIs in bank service PostmanCollection to manage basic information to work with the application. 
  * add account: the account number should be unique 
  * add card: the card number should be unique 
  * active card: is to active blocked card numbers

* to run tests, please set bank-service env profile to "test"
(its default profile is "dev")
