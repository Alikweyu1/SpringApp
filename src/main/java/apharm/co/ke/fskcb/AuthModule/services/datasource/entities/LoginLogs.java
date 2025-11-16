package apharm.co.ke.fskcb.AuthModule.services.datasource.entities;

import com.fasterxml.jackson.databind.node.ArrayNode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "login_logs_tb")
public class LoginLogs {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private Date loginDate;
    private boolean successful=false;
    private String fullName;
    private String channel;
//    private ArrayNode profileCode;
}

