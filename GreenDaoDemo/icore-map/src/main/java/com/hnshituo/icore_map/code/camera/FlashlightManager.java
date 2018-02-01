package com.hnshituo.icore_map.code.camera;

import android.os.IBinder;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author Wzh
 * @date 2016/7/14  14:02
 */
class FlashlightManager {
    private static final Object iHardwareService = getHardwareService();
    private static final Method setFlashEnabledMethod;

    static {
        setFlashEnabledMethod = getSetFlashEnabledMethod(iHardwareService);
    }

    private FlashlightManager() {
    }

    private static Object getHardwareService() {
        Class serviceManagerClass = maybeForName("android.os.ServiceManager");
        if(serviceManagerClass == null) {
            return null;
        } else {
            Method getServiceMethod = maybeGetMethod(serviceManagerClass, "getService", new Class[]{String.class});
            if(getServiceMethod == null) {
                return null;
            } else {
                Object hardwareService = invoke(getServiceMethod, (Object)null, new Object[]{"hardware"});
                if(hardwareService == null) {
                    return null;
                } else {
                    Class iHardwareServiceStubClass = maybeForName("android.os.IHardwareService$Stub");
                    if(iHardwareServiceStubClass == null) {
                        return null;
                    } else {
                        Method asInterfaceMethod = maybeGetMethod(iHardwareServiceStubClass, "asInterface", new Class[]{IBinder.class});
                        return asInterfaceMethod == null?null:invoke(asInterfaceMethod, (Object)null, new Object[]{hardwareService});
                    }
                }
            }
        }
    }

    private static Method getSetFlashEnabledMethod(Object iHardwareService) {
        if(iHardwareService == null) {
            return null;
        } else {
            Class proxyClass = iHardwareService.getClass();
            return maybeGetMethod(proxyClass, "setFlashlightEnabled", new Class[]{Boolean.TYPE});
        }
    }

    private static Class<?> maybeForName(String name) {
        try {
            return Class.forName(name);
        } catch (ClassNotFoundException var2) {
            return null;
        } catch (RuntimeException var3) {
            return null;
        }
    }

    private static Method maybeGetMethod(Class<?> clazz, String name, Class... argClasses) {
        try {
            return clazz.getMethod(name, argClasses);
        } catch (NoSuchMethodException var4) {
            return null;
        } catch (RuntimeException var5) {
            return null;
        }
    }

    private static Object invoke(Method method, Object instance, Object... args) {
        try {
            return method.invoke(instance, args);
        } catch (IllegalAccessException var4) {
            return null;
        } catch (InvocationTargetException var5) {
            return null;
        } catch (RuntimeException var6) {
            return null;
        }
    }

    static void enableFlashlight() {
        setFlashlight(true);
    }

    static void disableFlashlight() {
        setFlashlight(false);
    }

    private static void setFlashlight(boolean active) {
        if(iHardwareService != null) {
            invoke(setFlashEnabledMethod, iHardwareService, new Object[]{Boolean.valueOf(active)});
        }

    }
}
