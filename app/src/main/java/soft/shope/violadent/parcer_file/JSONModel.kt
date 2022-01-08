package soft.shope.violadent.parcer_file

interface JSONModelProtocol // type json model
interface JSONBodyProtocol // type body request
// product model
data class ItemsShop(var created: String? = "",
                     var currency: String? = "",
                     var encoding: String? = "",
                     var items: List<ItemsShopList>? = null): JSONModelProtocol

data class ItemsShopList( var id: String? = "",
                      var name_item: String? = "",
                      var model: String? = "",
                      var quantity: String? = "",
                      var image: String? = "",
                      var price: String? = "",
                      var title: String? = "",
                      var description: String? = "",
                      var tag: String? = "",
                      var keyword: String? = "",
                      var manufacturer: String? = "",
                      var link: String? = "",
                      var category_id: String? = "",
                      var category_description: String? = "",
                      var category_image: String? = "",
                      var category_name: String? = "",
                      var category_title: String? = "" ) : JSONModelProtocol


// model for categories
data class Category(var data: List<CategoryList>? = null) : JSONModelProtocol

data class CategoryList(
    var category_id: String? = "",
    var name: String? = "",
    var check: Boolean? = true
) : JSONModelProtocol


// body categories
data class CategoriesBody ( var password:String? = "dataFF121s",
                            var method:String? = "Categories",
                            var id:String? = "611" ) : JSONBodyProtocol



// for filling about us
data class ContactData ( val introduction:String =
    "Доставка по Україні можлива декількома кур'єрськими службами: Нова Пошта, Делівері, Укрпошта або Justin. ",
                         val contactPhone:String = "+380800335182",
                         val address:String = "м.Полтава, вул. Комарова 5",
                         val email:String = "info@vikisoft.kiev.ua",
                         val urlSite:String = "https://violadent.com",

                         val privacyPolicyUrl:String = "https://violadent.com/pol-conf/",
                         var facebookUrl:String = "https://www.facebook.com/royal.ltd/",
                         val faceBookUrlApp:String = "fb://user?username=royal.ltd",
                         val telegramUrl:String = "https://t.me/royal_ltd",

                         val viberUrl:String = "https://invite.viber.com/?g2=AQA2buxc8AgcEEnZWk0K6iPBMIAz9UGZ%2B0oxTbAbJJSRQxsPP%2FIpKB8nXCCbXk4A&lang=ru",
                         val youtubeUrl:String = "https://www.youtube.com/channel/UCoa7VBe1g_m6Peoc0bQj32Q",
                         val categoriesBody:CategoriesBody = CategoriesBody() )



//
//enum class Model {
//
//        A, B, C;
//
//    var a: String get() = ""
//        set(value) {}
//
//    fun a (){}
//
//    val b: String = "kdk"
//
//}
//
//interface Protocol{
//    fun getString()
//}
//
//open class ModelClass(private val string: String) : Protocol{
//
//    override fun getString() {
//        println(string)
//    }
//}
//
//class JSONModel(a: Protocol) : Protocol by a
//
//
////fun main () {
////    JSONModel(ModelClass("sukaString")).getString()
////
////}
//
//suspend fun main(): Unit = coroutineScope {
//    val job = Job()
//    launch(job) {
//        try {
//            delay(200)
//            println("Coroutine finished")
//        } finally {
//            println("Finally")
//            withContext(NonCancellable) {
//                delay(1000L)
//                println("Cleanup done")
//            }
//        }
//    }
//    delay(100)
//    job.cancelAndJoin()
//    println("Done")
//}










