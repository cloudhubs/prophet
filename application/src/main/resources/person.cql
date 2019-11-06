CREATE (jan:Person { name: 'Jan' })
CREATE (ian:Person { name: 'Ian' })
CREATE (jon:Person { name: 'Jon' })
create (jan)-[:TEAMMATE]->(ian)