package com.release.cpmsmobileapp.notices;

import android.content.Context;

import com.release.cpmsmobileapp.responsebody.NoticesResponse;
import com.release.cpmsmobileapp.utils.HelperUtils;

import java.util.Comparator;
import java.util.List;

public class NoticeUtils extends HelperUtils {
    Context context;

    public NoticeUtils(Context context) {
        super(context);
        this.context = context;
    }

    public void sortListByIOIssueDate(List<NoticesResponse> list) {
        Comparator<NoticesResponse> dateComparator = (o1, o2) -> {
            String dateStr1 = o1.getIo_assign_date();
            String dateStr2 = o2.getIo_assign_date();

            return dateStr2.compareTo(dateStr1);
        };
        list.sort(dateComparator);
    }
}
