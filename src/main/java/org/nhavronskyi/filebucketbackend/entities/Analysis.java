package org.nhavronskyi.filebucketbackend.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Analysis {
    private String harmless;
    private String suspicious;
    private String timeout;
    private String failure;
    private String malicious;
    private String undetected;
}