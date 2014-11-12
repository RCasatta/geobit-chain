# Redundant blockchain data provider

## Why?
Decentralized infrastructure should not have a single point of failure.
There are multiple excellent block explorer out there but we don't want to dependent on a single one.

## Solution
A failover solution that has multiple sources of information and ask every data to at least two different provider.
This way we can improve availability, dependability, safeness and performance of the data retrieved.

## Build
### Requirements
Java, Maven
### Build
```
mvn install
```
### Launch
```
java -jar target/chain/WEB-INF/lib/jetty-runner-8.1.0.RC5.jar target/chain.war
```
