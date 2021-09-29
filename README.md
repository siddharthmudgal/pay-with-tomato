# Paying with a tomato



## About

Tomato challenge.



## Pre-requisites

Docker desktop



## How to run

#### start containers

```bash
cd domaintransactions
docker-compose -f docker-compose.yml up
```

#### stop containers

```bash
cd domaintransactions
docker-compose -f docker-compose.yml down
```



## System Design

![System design](./pay-with-a-tomato-system-design.jpeg)



## Deployment workflow

![deployment-workflow](./tomatopay-workflow.jpeg)



## Deployment design

![deployment design](./deployment-design.jpeg)



## Code coverage report

| com.tomatopay | 91% (11/12) | 90% (29/32) | 94% (81/86) |
| ------------- | ----------- | ----------- | ----------- |

