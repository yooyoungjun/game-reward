package game;

import game.config.kafka.KafkaProcessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PolicyHandler{

    @Autowired
    RewardRepository rewardRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void onStringEventListener(@Payload String eventString){

    }

    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverMissionAchieved_Allocate(@Payload MissionAchieved missionAchieved){

        if(missionAchieved.isMe()){
            Reward reward = new Reward();
            reward.setMissionId(missionAchieved.getId());
            reward.setCustomerId(missionAchieved.getCustomerId());
            reward.setStatus("RewardAllocated");

            rewardRepository.save(reward);
            System.out.println("##### listener Allocate : " + missionAchieved.toJson());
        }
    }
    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverIssued_UpdateStatus(@Payload Issued issued){

        if(issued.isMe()){
            Optional<Reward> rewardOptional = rewardRepository.findById(issued.getRewardId());
            Reward reward = rewardOptional.get();
            reward.setStatus(issued.getStatus());

            rewardRepository.save(reward);

            System.out.println("##### listener UpdateStatus : " + issued.toJson());
        }
    }

}
