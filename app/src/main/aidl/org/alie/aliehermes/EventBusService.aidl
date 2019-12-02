// EventBusService.aidl
package org.alie.aliehermes;
import org.alie.aliehermes.Request; // 不管是是不是同一个包下，都要导入包
import org.alie.aliehermes.Response; // 不管是是不是同一个包下，都要导入

interface EventBusService {

    Response send(in Request request);
}
