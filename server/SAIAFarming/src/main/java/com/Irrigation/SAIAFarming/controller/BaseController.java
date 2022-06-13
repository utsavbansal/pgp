package com.Irrigation.SAIAFarming.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;


public abstract class BaseController {
    @Autowired
    MessageSource messageSource;

//    private static final MediaType[] supportedMediaTypes =
//            {MediaType.APPLICATION_JSON_TYPE, MediaType.APPLICATION_XML_TYPE};
//    protected static final List<Variant> mtVariantList = getVariantList(supportedMediaTypes);
//
//    static List<Variant> getVariantList(MediaType[] mt) {
//        Variant.VariantListBuilder b = Variant.VariantListBuilder.newInstance();
//        for (MediaType t : mt) {
//            b.mediaTypes(t).add();
//        }
//        return b.build();
//    }



}
