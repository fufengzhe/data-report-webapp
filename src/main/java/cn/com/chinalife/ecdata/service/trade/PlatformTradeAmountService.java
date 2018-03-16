package cn.com.chinalife.ecdata.service.trade;

import cn.com.chinalife.ecdata.entity.trade.Premium;

import java.util.List;

/**
 * Created by xiexiangyu on 2018/3/15.
 */
public interface PlatformTradeAmountService {
    List<Premium> getPlatformTradeAmount();

    List<Premium> getPlatformTradeNum();

    Premium getPlatformSignRatio();
}
