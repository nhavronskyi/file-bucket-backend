package org.nhavronskyi.filebucketbackend.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Analysis {
    private int malicious;
    private int suspicious;
    private int undetected;
    private String fileStatus;
}