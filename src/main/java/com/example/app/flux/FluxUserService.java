package com.example.app.flux;

import com.example.app.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.lang.invoke.MethodHandles;
import java.time.Duration;

@Service
public class FluxUserService {


    private static final Logger logger= LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());


    public Flux<User> createSingleUser(){

        return Flux.just(new User(1L,"akshit",25));

    }

    public Flux<User> createFluxOfUsers(){

        return getFluxOfUsers();
    }


    public Flux<String> getUserNames(){
    		
        return getFluxOfUsers().map(user -> user.getUserName()).doOnNext( name -> System.out.println(" Name "+name));
    }

    public Flux<User> getUsersBelowAge(Long age){

        return getFluxOfUsers().filter( user -> user.getAge() < age);
    }

    public Flux<User> combineTwoFluxsIntoOne(){
        return Flux.first(firstSetOfUsers()).concatWith(secondSetOfUsers());
    }



    public Flux<User> zipUsers(){

        Flux<String> userNames=Flux.just("AKSHIT","AJAY","SACHIN");
        Flux<Integer> userAges=Flux.just(25,34,40);
        Flux<Long> userIds=Flux.just(1L,2L,3L);
        return Flux.zip(userIds,userNames,userAges).map( pair -> new User(pair.getT1(),pair.getT2(),pair.getT3()));
    }

    private Flux<User> getFluxOfUsers(){
        return Flux.just( new User(1L,"akshit",25),
                new User(2L,"DHONI",35),
                new User(3L,"SACHIN",45),
                new User(4L,"VIRAT",28),
                new User(5L,"ROHIT",30),
                new User(6L,"DHAWAN",28),
                new User(7L,"KUMBLE",42),
                new User(8L,"JOSHI",46),
                new User(9L,"GAVASKAR",65),
                new User(10L,"KAPILDEV",55),
                new User(11L,"SRINATH",50),
                new User(12L,"SACHIN",45));
    }

    private Flux<User>  firstSetOfUsers(){

        return Flux.just( new User(1L,"RAMESH",24),
                new User(2L,"SURESH",24),
                new User(3L,"SAURBH",24));
    }

    private Flux<User> secondSetOfUsers(){

        return Flux.just( new User(4L,"ROHINI",24),
                new User(5L,"MOHINI",24),
                new User(6L,"KMINI",24));
    }

    private Flux<User> secondSetOfUsersWithDelay(){
        return secondSetOfUsers().delayElements(Duration.ZERO);
    }


}
