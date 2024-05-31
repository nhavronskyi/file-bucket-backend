package org.nhavronskyi.filebucketbackend.entities.users;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.NonNull;
import lombok.ToString;
import org.nhavronskyi.filebucketbackend.service.AwsS3Service;

import java.util.Map;


@ToString
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class UserS3Info {
    private final long id;
    private final Map<String, Long> files;
    private final double size;

    public UserS3Info(@NonNull AwsS3Service service, long id) {
        files = service.getDirectoryFilesNamesAndSizes(id);
        this.id = id;
        this.size = service.getDirectorySize(id);
    }
}

