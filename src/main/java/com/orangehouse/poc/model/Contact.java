package com.orangehouse.poc.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@ApiModel
@Entity
@Table(name = "contacts")
@GenericGenerator(name = "jpa-uuid", strategy = "uuid")
@Data
@NoArgsConstructor
public class Contact
{
    @ApiModelProperty(
            value = "unique id, auto-generated and cannot be assigned.")
    @Id
    @GeneratedValue(generator = "jpa-uuid")
    @Setter(value = AccessLevel.PRIVATE)
    @Column(length = 32)
    private String id;

    @ApiModelProperty(value = "first name")
    @Column(name = "first_name", nullable = false)
    private String firstName;

    @ApiModelProperty(value = "last name")
    @Column(name = "last_name", nullable = false)
    private String lastName;

    @ApiModelProperty(
            value = "email address,  should be unique and not nullable.")
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @ApiModelProperty(value = "phone number")
    @Column(name = "phone_no", nullable = false)
    private String phoneNo;

    @ApiModelProperty(value = "remark")
    @Column(name = "remark")
    private String remark;

    @Lob
    @JsonIgnore
    @Column(name = "avatar")
    private byte[] avatar;

    public Contact(String firstName, String lastName, String email,
                   String phoneNo, String remark)
    {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNo = phoneNo;
        this.remark = remark;
    }

}
