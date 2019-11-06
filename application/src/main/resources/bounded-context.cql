CREATE (system:SYSTEM { name: 'Transaction system' })
CREATE (module:MODULE { name: 'Terminal' })
CREATE (system)-[:SYSTEM_MODULE]->(module)
