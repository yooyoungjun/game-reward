package game;

import javax.persistence.*;
import org.springframework.beans.BeanUtils;
import java.util.List;

@Entity
@Table(name="Reward_table")
public class Reward {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    private Long missionId;
    private Long customerId;
    private String status;

    @PostPersist
    public void onPostPersist(){
        Allocated allocated = new Allocated();
        BeanUtils.copyProperties(this, allocated);
        allocated.publishAfterCommit();


    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public Long getMissionId() {
        return missionId;
    }

    public void setMissionId(Long missionId) {
        this.missionId = missionId;
    }
    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }




}
