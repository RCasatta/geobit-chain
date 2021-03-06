# Rebecca
Redundant blockchain data provider
## Why?
Decentralized infrastructure should not have a single point of failure.
There are multiple excellent block explorer out there but we don't want to be dependent on a single one.

## Solution
A fail-over solution that has multiple sources of information and ask every data to at least two different providers.
This way we can improve:
* __Availability:__ Remove any dependency to a single endpoint that could disappear or being under DDoS.
* __Safeness:__ Sometimes data are not coherent, asking two sources the error probability is reduced to almost zero.
* __Performance:__ Waiting the fastest provider and not the slowest. Bring the information you need where you need with caching improve response time. Leverage the 80/20 rule.
* __Uniformness:__ There are many interface for the same information, this way you have to deal with just one format.

Have a look at [chain.geobit.io](http://chain.geobit.io)

## Build
java, mvn and git are required to build and run
```
git clone https://github.com/RCasatta/geobit-chain
cd geobit-chain
mvn install -Dmaven.test.skip=true
java -jar target/chain/WEB-INF/lib/jetty-runner-8.1.0.RC5.jar target/chain.war
```
Open your browser at [localhost:8080](http://localhost:8080)

PS Needing efforts to adjust testing classes and remove the skip test flag 

## Block Explorers
* https://www.biteasy.com
* https://blockchain.info
* http://www.blockcypher.com
* https://blockexplorer.com
* http://block.io
* https://blockr.io
* https://www.blocktrail.com
* https://chain.com
* http://coinb.in
* https://helloblock.io
* https://insight.bitpay.com
* http://mychain.io
* https://chain.so

## API Keys
You should ask providers a personalized API key and update the ApiKeys.java source file 

## Roadmap
* Add more providers.
* Use generics when classes has type name in their name.
* Documentation