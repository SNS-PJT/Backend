package com.project.sns.domain.post.dto.validation;

import com.project.sns.global.exception.NotUploadFileException;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.Locale;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

public class AllowedContentTypeValidator implements
        ConstraintValidator<AllowedContentType, MultipartFile> {

    private String[] allowedTypes;
    private String[] allowedExtensions;

    @Override
    public boolean isValid(MultipartFile multipartFile, ConstraintValidatorContext context) {
        if (multipartFile == null || multipartFile.isEmpty()) {
            throw new NotUploadFileException("파일을 하나 이상 업로드 해주세요.");
        }
        return isAllowedType(multipartFile) && isAllowedExtension(multipartFile);
    }

    @Override
    public void initialize(AllowedContentType constraintAnnotation) {
        allowedTypes = constraintAnnotation.allowedTypes();
        allowedExtensions = constraintAnnotation.allowedExtensions();
    }

    private boolean isAllowedType(MultipartFile multipartFile) {
        return Arrays.asList(allowedTypes)
                     .contains(multipartFile.getContentType());
    }

    private boolean isAllowedExtension(MultipartFile multipartFile) {

        String originalFileName = multipartFile.getOriginalFilename();
        String fileExtension = StringUtils.getFilenameExtension(originalFileName)
                                          .toLowerCase(Locale.ROOT);

        return Arrays.asList(allowedExtensions)
                     .contains(fileExtension);
    }
}
