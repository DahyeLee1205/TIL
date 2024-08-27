# Spring Batch

자세한 내용은 [notion 링크](https://continuous-bagel-d02.notion.site/Spring-Batch-838962ce362948cf8a80befb1ae63578?pvs=4) 참고

Spring Batch란?  
로깅/추적, 트랜잭션 관리, 작업 처리 통계, 작업 재시작, 건너뛰기, 리소스 관리를 포함하여 대량의 레코드를 처리하는 데 필수적인 재사용 가능한 기능을 제공  


## 특징
- Transaction 관리
- 청크 기반 처리 : 데이터를 나누어 처리하는 방식
- [선언적 I/O](https://www.notion.so/Declarative-I-O-99d48139fac64bdabdc09fd3468e83e9?pvs=21) : 개발자가 결과나 목표를 선언하는 방식
- 시작/중지/재시작
- 다시 시도/건너뛰기
- 웹 기반 관리 인터페이스( [Spring Cloud Data Flow](https://cloud.spring.io/spring-cloud-dataflow) )


## Spring Batch 용어 

**Job**

배치 처리과정을 하나의 단위로 만들어 놓은 객체, 배치 처리과정에 있어 전체 계증 최상단에 위치  

**JobInstance**

JobInstance는 Job의 실행의 단위를 나타냅니다.   
Job을 실행시키게 되면 하나의 JobInstance가 생성되게 됩니다.   전체 배치 프로 세스를 캡슐화 하는 엔티티, 실행시킬 작업

ex)  1월 1일 실행, 1월 2일 실행을 하게 되면 각각의 JobInstance 가 생성되고, 만약 1월 1일에 실행한 JobInstance 가 실패하여 이후 재실행 하더라도 이 JobInstance 는 1월 1일에 대한 데이터만 처리.  


**JobParameters**

Job을 실행하는 데 사용하는 파라미터의 집합, Job이 실행되는 동안 JobInstance 의 식별 외에도 매개변수 역할로 사용.  
String, Double, Long, Date 4가지 형식만을 지원


**JobExecution**

JobInstance 에서 실행 시도에 대한 객체, 실행에 대한 JobExecution은 개별로 생성된다.
JobInstance 실행에 대한 상태, 시작시간, 종료시간, 생성시간 등의 정보를 가짐.

ex) 1월 1일에 실행한 JobInstance 가 실패하여 재실행을 하여도 동일한 JobInstance 를 실행시키지만 이 2번에 실행에 대한 Job Execution 은 개별로 생성.  


**Step**

- Job의 배치처리를 정의하고 순차적인 단계를 캡슐화
- Job은 최소 1개 이상의 Step을 가져야 하고, Job의 실제 일괄처리를 제어하는 모든 정보를 포함
- Step의 내용은 개발자의 선택에 따라 구성
- Tasklet/Chunk 지향 처리방식을 지원한다.
    
    
    *Tasklet 처리방식*
    
    - 단순한 데이터 프로세스 처리에 적합한 모델
    - SQL 1회 명령 등 단순하거나, 작업 프로세스의 표준화가 어려운 복잡한 경우에 Custom 작업 생성을 위해 사용
    
    *Chunk 처리방식*
    
    - 메모리에 가지고 있기 너무 많고, 큰 데이터들을 효율적으로 처리하는 데 적합
    - 일정양의 데이터를 일괄적으로 read/process/write 프로세스 흐름에 따라 표준화 하여 작업을 구현
    - 하나의 Transaction 안에서 처리할 item의 덩어리
    - chunk size가 10이면 transaction 안에서 10개의 item 에 대해 처리하고 commit.

**StepExecution**
- JobExecution 과 동일하게 Step 실행 시도에 대한 객체
- 실제 시작이 될 때 생성.
- 실행에 대한 다양한 정보를 가진다 : 시작시간, 종료시간, 상태, 종료상태, Commit Count, ItemCount, Skip Count 등..

**Execution Context** 

- Job에서 데이터를 공유할 수 있는 데이터 저장소
- Spring Batch에서는 JobExecutionContext, StepExecutionContext를 지원
    - *JobExecutionContext* :  Commit 시점에 저장
    - *StepExecutionContext* : 실행 사이에 저장 ExecutionContext 를 통해 Step 간 Data 공유가 가능하고, Job 실패시 ExecutionContext 를 통해 마지막 실행 값을 재구성 할 수 있음.

**JobRepository**
- 수행되는 Job 의 모든 정보를 담고 있는 저장소.
- 어떠한 Job 이 언제 수행되었고, 언제 종료하고, 몇 번 실행되었고, 실행에 대한 결과가 어떤지 등의 Batch 수행과 과련된 모든 Meta Data 가 저장.
- Job 이 실행되게 되면 JobRepository 에 JobExecution 과 StepExecution 을 생성하고, JobRepository 에서 Execution 정보들을 저장하고 조회하며 사용.