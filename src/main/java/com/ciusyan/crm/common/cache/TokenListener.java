package com.ciusyan.crm.common.cache;


import com.ciusyan.crm.pojo.dto.UserPermissionDto;
import org.ehcache.event.CacheEvent;
import org.ehcache.event.CacheEventListener;

public class TokenListener implements CacheEventListener {


    @Override
    public void onEvent(CacheEvent cacheEvent) {
        String token = (String) cacheEvent.getKey();

        switch (cacheEvent.getType()) {
            case CREATED: {
                UserPermissionDto user = (UserPermissionDto) cacheEvent.getNewValue();
                Caches.put(user.getUser().getId(), token);
                break;
            }
            case EXPIRED:
            case REMOVED: {
                UserPermissionDto user = (UserPermissionDto) cacheEvent.getOldValue();
                Caches.remove(user.getUser().getId());
                break;
            }
            default:
                break;
        }

    }
}
