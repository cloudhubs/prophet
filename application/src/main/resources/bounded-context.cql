CREATE (system:SYSTEM { name: 'Transaction system' })

CREATE (terminal:MODULE { name: 'Terminal' })
CREATE (atm:MODULE { name: 'ATM'})
CREATE (tollbooth:MODULE { name: 'TollBooth'})



CREATE (system)-[:SYSTEM_MODULE]->(terminal)
CREATE (system)-[:SYSTEM_MODULE]->(atm)
CREATE (system)-[:SYSTEM_MODULE]->(tollbooth)


CREATE (merchant:DBCLASS {name: 'edu.baylor.Terminal.Merchant'})
CREATE (terminal_place:DBCLASS {name: 'edu.baylor.Terminal.Place'})
CREATE (terminal_transaction:DBCLASS {name: 'edu.baylor.Terminal.Transaction'})
CREATE (terminal)-[:HAS_A_CLASS]->(merchant)
CREATE (terminal)-[:HAS_A_CLASS]->(terminal_place)
CREATE (terminal)-[:HAS_A_CLASS]->(terminal_transaction)


CREATE (operator:DBCLASS {name: 'edu.baylor.ATM.operator'})
CREATE (administrator:DBCLASS {name: 'edu.baylor.TollBooth.Administrator'})

CREATE (pos:DBCLASS {name: 'edu.baylor.Terminal.POS'})

CREATE (atm_Place:DBCLASS {name: 'edu.baylor.ATM.Place'})
CREATE (tollbooth_Location:DBCLASS {name: 'edu.baylor.TollBooth.Location'})

CREATE (atm_transaction:DBCLASS {name: 'edu.baylor.ATM.Transaction'})
CREATE (tollbooth_transaction:DBCLASS {name: 'edu.baylor.TollBooth.Transaction'})