package com.williamm56i.bertolt.persistence.vo;

import lombok.Data;

import java.util.Date;

@Data
public class BatchJobTriggerConfig {

    String jobName;

    String jobDesc;

    String beanName;

    String triggerName;

    String cronTrigger;

    String enable;

    String createId;

    Date createDate;

    String updateId;

    Date updateDate;
}
