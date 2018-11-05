//package crEmgcy;
//
//import java.util.Calendar;
//import java.util.Date;
//
//import com.emgcy.core.system.video.bean.VideoAlbumEs;
//
//import net.sf.json.JSONObject;
//
//public class ee {
////	public static void main(String[] args) throws Exception {
////        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
////        String str1 = "2012-02";
////        String str2 = "2010-01";
////        Calendar bef = Calendar.getInstance();
////        Calendar aft = Calendar.getInstance();
////        bef.setTime(sdf.parse(str1));
////        aft.setTime(sdf.parse(str2));
////        int result = aft.get(Calendar.MONTH) - bef.get(Calendar.MONTH);
////        int month = (aft.get(Calendar.YEAR) - bef.get(Calendar.YEAR)) * 12;
////        System.out.println(result);
////        System.out.println(month);
////        System.out.println(Math.abs(month + result));
////    }
//	public static int getAge(Date dateOfBirth) {
//        int age = 0;
//        Calendar born = Calendar.getInstance();
//        Calendar now = Calendar.getInstance();
//        if (dateOfBirth != null) {
//            now.setTime(new Date());
//            born.setTime(dateOfBirth);
//            if (born.after(now)) {
//                throw new IllegalArgumentException("年龄不能超过当前日期");
//            }
//            age = now.get(Calendar.YEAR) - born.get(Calendar.YEAR);
//            int nowDayOfYear = now.get(Calendar.DAY_OF_YEAR);
//            int bornDayOfYear = born.get(Calendar.DAY_OF_YEAR);
//            System.out.println("nowDayOfYear:" + nowDayOfYear + " bornDayOfYear:" + bornDayOfYear);
//            if (nowDayOfYear < bornDayOfYear) {
//                age -= 1;
//            }
//        }
//        return age;
//    }
//
//    public static void main(String[] args) throws Exception {
////        SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd");
////        java.util.Date mydate = myFormatter.parse("2017-07-23");
////        System.out.println(getAge(mydate));
//
//    	String jsonStr="{\"videoEsList\":[{\"summary\":\"sumoooop述1\",\"videoUuid\":null,\"imagePath\":null,\"praiseNum\":null,\"collectNum\":null,\"classifyUuid\":null,\"recommend\":null,\"label\":\"1,lll2\",\"title\":null,\"uuid\":null,\"userId\":null,\"commentNum\":null,\"videoPath\":null,\"coverUuid\":null,\"createTime\":\"2018-02-01 07:02\",\"orgHospitalId\":1,\"id\":null,\"state\":3,\"browseNum\":null,\"classifyName\":\"classname1\"},{\"summary\":\"sumoooop述2\",\"videoUuid\":null,\"imagePath\":null,\"praiseNum\":null,\"collectNum\":null,\"classifyUuid\":null,\"recommend\":null,\"label\":\"2,lll3\",\"title\":null,\"uuid\":null,\"userId\":null,\"commentNum\":null,\"videoPath\":null,\"coverUuid\":null,\"createTime\":\"2018-02-01 07:02\",\"orgHospitalId\":1,\"id\":null,\"state\":3,\"browseNum\":null,\"classifyName\":\"classname2\"},{\"summary\":\"sumoooop述3\",\"videoUuid\":null,\"imagePath\":null,\"praiseNum\":null,\"collectNum\":null,\"classifyUuid\":null,\"recommend\":null,\"label\":\"3,lll4\",\"title\":null,\"uuid\":null,\"userId\":null,\"commentNum\":null,\"videoPath\":null,\"coverUuid\":null,\"createTime\":\"2018-02-01 07:02\",\"orgHospitalId\":1,\"id\":null,\"state\":3,\"browseNum\":null,\"classifyName\":\"classname3\"}],\"id\":2,\"albumEs\":{\"summary\":\"summary\",\"commentNum\":null,\"createTime\":null,\"imagePath\":null,\"orgHospitalId\":1,\"id\":null,\"label\":\"label\",\"videoNum\":null,\"title\":\"好的\",\"browseNum\":null,\"uuid\":null,\"userId\":9999}}";
//    	JSONObject fromObject = JSONObject.fromObject(jsonStr);
//    	VideoAlbumEs videoAlbumEs = (VideoAlbumEs) JSONObject.toBean(fromObject, VideoAlbumEs.class);
//    	System.out.println(videoAlbumEs);
//    }
//}
