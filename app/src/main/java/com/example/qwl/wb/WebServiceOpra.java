package com.example.qwl.wb;

/**
 * Created by qwl on 2017/5/10.
 */
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;


class WebServiceOpra {

    // 自定义类型参数
    public static String people_name(String totxt) {
        String SERVER_URL = "http://192.168.1.108:8080/axis2/services/qwl.SvmService";
        final HttpTransportSE httpSE = new HttpTransportSE(SERVER_URL);

        String PACE = "http://qwl.com";
        String M_NAME = "Svm";
        SoapObject soapObject = new SoapObject(PACE, M_NAME);
        //调用方法参数值
        //String totxt = "236:1 237:1 262:1 263:1 264:1 265:1 266:1 289:1 290:1 291:1 292:1 293:1 305:1 306:1 316:1 317:1 318:1 319:1 331:1 332:1 333:1 334:1 343:1 344:1 345:1 346:1 358:1 359:1 360:1 361:1 362:1 371:1 372:1 373:1 374:1 384:1 385:1 386:1 387:1 388:1 389:1 400:1 401:1 402:1 403:1 410:1 411:1 412:1 413:1 414:1 415:1 429:1 430:1 431:1 435:1 436:1 437:1 438:1 439:1 440:1 441:1 457:1 458:1 459:1 460:1 461:1 462:1 463:1 464:1 465:1 466:1 467:1 484:1 485:1 486:1 487:1 488:1 489:1 490:1 491:1 492:1 493:1 511:1 512:1 513:1 514:1 515:1 516:1 517:1 518:1 539:1 540:1 541:1 542:1 543:1";
        soapObject.addProperty("args", totxt);
        final SoapSerializationEnvelope soapserial = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        soapserial.bodyOut = soapObject;
        // 设置与.NET提供的Web service保持有良好的兼容性
        //soapserial.dotNet = true;
        try {
            httpSE.call(PACE + M_NAME, soapserial);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }
        SoapObject result = (SoapObject) soapserial.bodyIn;
        System.out.println("resultaaaa:" + result);
        if(result==null) return "null";
        return result.toString();
    }


}