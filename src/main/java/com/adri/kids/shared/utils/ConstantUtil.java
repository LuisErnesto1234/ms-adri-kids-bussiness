package com.adri.kids.shared.utils;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ConstantUtil {
    public static final int TIME_OUT_TRANSACTION = 6;
    public static final int MAX_SIZE = 50;
    public static final int MIN_SIZE = 1;
    public static final int MIN_PAGE = 0;
    // Properties for images uploads to s3 aws
    public static final int MAX_WIDTH_UPLOAD_IMG = 800;
    public static final int MAX_HEIGHT_UPLOAD_IMG = 800;
    public static final int WEBP_QUALITY_IMG = 80;
    public static final String WEBP_CONTENT_TYPE = "image/webp";
}
