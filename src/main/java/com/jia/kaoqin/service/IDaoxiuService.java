package com.jia.kaoqin.service;

import com.jia.common.po.JSONData;
import com.jia.kaoqin.po.DaoxiuEntity;

import javax.servlet.http.HttpSession;

/**
 * Created by jiaxl on 2016/10/14.
 */
public interface IDaoxiuService {
    JSONData submitDaoxiu(HttpSession session, DaoxiuEntity entity);
    JSONData chexiaoDaoxiu(HttpSession session,DaoxiuEntity entity);
}
