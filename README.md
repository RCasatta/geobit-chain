# Redundant blockchain data provider


## Why?
Decentralized infrastructure should not have a single point of failure.
There are multiple excellent block explorer out there but we don't want to be dependent on a single one.

## Solution
A fail-over solution that has multiple sources of information and ask every data to at least two different providers.
This way we can improve availability, dependability, safeness and performance of the data retrieved. Have a look at [chain.geobit.io](http://chain.geobit.io)

## Build
java, mvn and git are required to build and run
```
git clone https://github.com/RCasatta/geobit-chain
cd geobit-chain
mvn install
java -jar target/chain/WEB-INF/lib/jetty-runner-8.1.0.RC5.jar target/chain.war
```
Open your browser at [localhost:8080](http://localhost:8080)

