package org.alie.aliehermes;

import org.alie.aliehermes.annotion.ClassId;
import org.alie.aliehermes.annotion.MethodId;

@ClassId("org.alie.aliehermes.UserManager")
public interface IUserManager {
    @MethodId("getUser")
    String getUser();
}
