/* create the primitive types of the system */
CREATE (int:PRIMITIVE {name : "int"})
CREATE (long:PRIMITIVE {name : "long"})
CREATE (double:PRIMITIVE {name : "double"})
CREATE (short:PRIMITIVE {name : "short"})
CREATE (char:PRIMITIVE {name : "char"})
CREATE (string:PRIMITIVE {name : "string"})

/*CREATE the system*/
CREATE (system:SYSTEM { name: 'Transaction system' })

/*the modules of the system */
CREATE (terminal:MODULE { name: 'Terminal' })
CREATE (atm:MODULE { name: 'ATM'})
CREATE (tollbooth:MODULE { name: 'TollBooth'})

CREATE (system)-[:SYSTEM_MODULE]->(terminal)
CREATE (system)-[:SYSTEM_MODULE]->(atm)
CREATE (system)-[:SYSTEM_MODULE]->(tollbooth)

/* MODULE ONE ****************************************************************************************/

/*an abstraction of an employee in a terminal */
CREATE (employee_info:DBCLASS {name: 'edu.baylor.Terminal.Employee'})
CREATE (employee_name:VARIABLE{name : "name"})
CREATE (employee_name)-[:HAS_TYPE]->(string)
CREATE (employee_wage:VARIABLE{name : "wage"})
CREATE (employee_wage)-[:HAS_TYPE]->(int)
CREATE (employee_info)-[:CLASS_VARIABLE]->(employee_name)
CREATE (employee_info)-[:CLASS_VARIABLE]->(employee_wage)
CREATE (employee_employment_type:VARIABLE{name : "employee_type"})
CREATE (employee_employment_type)-[:HAS_TYPE]->(string)
CREATE (employee_info)-[:CLASS_VARIABLE]->(employee_employment_type)
CREATE (terminal)-[:HAS_A_CLASS]->(employee_info)

/*the merchant associated with a POS */
CREATE (merchant:DBCLASS {name: 'edu.baylor.Terminal.Merchant'})
CREATE (merchant_info:VARIABLE{name : "info"})
CREATE (merchant)-[:CLASS_VARIABLE]->(merchant_info)
CREATE (merchant_info)-[:HAS_TYPE]->(employee_info)
CREATE (terminal)-[:HAS_A_CLASS]->(merchant)

/* the transaction of a terminal */
CREATE (terminal_transaction:DBCLASS {name: 'edu.baylor.Terminal.Transaction'})
CREATE (terminal_transaction_id:VARIABLE{name : "id"})
CREATE (terminal_transaction_cashier:VARIABLE{name : "cashier"})
CREATE (terminal_transaction)-[:CLASS_VARIABLE]->(terminal_transaction_id)
CREATE (terminal_transaction)-[:CLASS_VARIABLE]->(terminal_transaction_cashier)
CREATE (terminal)-[:HAS_A_CLASS]->(terminal_transaction)

/* the point of sale of the terminal*/
CREATE (pos:DBCLASS {name: 'edu.baylor.Terminal.POS'})
CREATE (terminal_city:VARIABLE{name : "city"})
CREATE (terminal_city)-[:HAS_TYPE]->(string)
CREATE (terminal_state:VARIABLE{name : "state"})
CREATE (terminal_state)-[:HAS_TYPE]->(string)
CREATE (terminal_country:VARIABLE{name : "country"})
CREATE (terminal_country)-[:HAS_TYPE]->(string)
CREATE (terminal_address:VARIABLE{name : "address"})
CREATE (terminal_address)-[:HAS_TYPE]->(string)
CREATE (pos)-[:CLASS_VARIABLE]->(terminal_city)
CREATE (pos)-[:CLASS_VARIABLE]->(terminal_state)
CREATE (pos)-[:CLASS_VARIABLE]->(terminal_country)
CREATE (pos)-[:CLASS_VARIABLE]->(terminal_address)
CREATE (terminal)-[:HAS_A_CLASS]->(pos)

/* a cashier of a terminal  */
CREATE (cashier:DBCLASS {name: 'edu.baylor.Terminal.Cashier'})
create (cashier_info:VARIABLE{name : "info"})
CREATE (cashier)-[:CLASS_VARIABLE]->(cashier_info)
CREATE (cashier_info)-[:HAS_TYPE]->(employee_info)
CREATE (terminal)-[:HAS_A_CLASS]->(cashier)

/* MODULE TWO ****************************************************************************************/

/*the actual atm */
CREATE (atm_physical:DBCLASS {name: 'edu.baylor.ATM.ATM'})
CREATE (atm)-[:HAS_A_CLASS]->(atm_physical)
CREATE (atm_id:VARIABLE{name : "id"})
CREATE (atm_id)-[:HAS_TYPE]->(int)
CREATE (atm_physical)-[:CLASS_VARIABLE]->(atm_id)


/*the location of an ATM */
CREATE (atm_Place:DBCLASS {name: 'edu.baylor.ATM.Place'})
CREATE (atm)-[:HAS_A_CLASS]->(atm_Place)
CREATE (atm_city:VARIABLE{name : "city"})
CREATE (atm_city)-[:HAS_TYPE]->(string)
CREATE (atm_state:VARIABLE{name : "state"})
CREATE (atm_state)-[:HAS_TYPE]->(string)
CREATE (atm_country:VARIABLE{name : "country"})
CREATE (atm_country)-[:HAS_TYPE]->(string)
CREATE (atm_address:VARIABLE{name : "address"})
CREATE (atm_address)-[:HAS_TYPE]->(string)
CREATE (atm_region_code:VARIABLE{name : "regionCode"})
CREATE (atm_Place)-[:CLASS_VARIABLE]->(atm_city)
CREATE (atm_Place)-[:CLASS_VARIABLE]->(atm_state)
CREATE (atm_Place)-[:CLASS_VARIABLE]->(atm_country)
CREATE (atm_Place)-[:CLASS_VARIABLE]->(atm_address)
CREATE (atm_Place)-[:CLASS_VARIABLE]->(atm_region_code)

/*the operator of an ATM */
CREATE (operator:DBCLASS {name: 'edu.baylor.ATM.operator'})
create (operator_name:VARIABLE{name : "name"})
CREATE (operator_name)-[:HAS_TYPE]->(string)
CREATE (operator)-[:CLASS_VARIABLE]->(operator_name)
CREATE (operator_atm:VARIABLE{name : "atms"})
CREATE (operator_atm)-[:HAS_TYPE]->(atm_physical)
CREATE (operator)-[:CLASS_VARIABLE]->(operator_atm)
CREATE (atm)-[:HAS_A_CLASS]->(operator)

/*a transaction for an ATM */
CREATE (atm_transaction:DBCLASS {name: 'edu.baylor.ATM.Transaction'})
CREATE (atm_transaction_id:VARIABLE{name : "id"})
CREATE (atm_transaction_id)-[:HAS_TYPE]->(long)
CREATE (atm_transaction)-[:CLASS_VARIABLE]->(atm_transaction_id)
CREATE (atm_transaction_timestamp:VARIABLE{name : "time"})
CREATE (atm_transaction_timestamp)-[:HAS_TYPE]->(string)
CREATE (atm_transaction)-[:CLASS_VARIABLE]->(atm_transaction_timestamp)
CREATE (atm)-[:HAS_A_CLASS]->(atm_transaction)

/* MODULE THREE ****************************************************************************************/


CREATE (administrator:DBCLASS {name: 'edu.baylor.TollBooth.Administrator'})
CREATE (tollbooth_Location:DBCLASS {name: 'edu.baylor.TollBooth.Location'})
CREATE (tollbooth_Transaction:DBCLASS {name: 'edu.baylor.TollBooth.Transaction'})

CREATE (tollbooth)-[:HAS_A_CLASS]->(administrator)
CREATE (tollbooth)-[:HAS_A_CLASS]->(tollbooth_Location)
CREATE (tollbooth)-[:HAS_A_CLASS]->(tollbooth_Transaction)