package com.example.mapd726_groupproject_team3_agriapp.Fragments

import android.app.Activity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.navigation.Navigation
import com.example.mapd726_groupproject_team3_agriapp.Adapter.CheckoutPageItemAdapter.CheckoutProductAdapter
import com.example.mapd726_groupproject_team3_agriapp.DataModels.CartModel
import com.example.mapd726_groupproject_team3_agriapp.DataModels.OrderModel
import com.example.mapd726_groupproject_team3_agriapp.DataModels.OrderedProductsModel
import com.example.mapd726_groupproject_team3_agriapp.R
import com.example.mapd726_groupproject_team3_agriapp.Utils.UserManager
import com.example.mapd726_groupproject_team3_agriapp.databinding.FragmentCheckoutBinding
import com.github.kittinunf.fuel.httpPost
import com.github.kittinunf.fuel.json.responseJson
import com.github.kittinunf.result.Result
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.stripe.android.PaymentConfiguration
import com.stripe.android.paymentsheet.PaymentSheet
import com.stripe.android.paymentsheet.PaymentSheetResult
import dagger.hilt.android.internal.managers.FragmentComponentManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.math.BigDecimal
import java.util.*
import javax.inject.Inject


class CheckoutFragment : Fragment() {

    lateinit var binding  : FragmentCheckoutBinding

    @Inject
    lateinit var userManager: UserManager

    lateinit var cartList : ArrayList<CartModel>
    lateinit var orderedProductsList : List<OrderedProductsModel>
    var cartTotal : Double? = 0.0
    var cartTotalTax : Double ? = 0.0
    var cartTotalPayable : Double? = 0.0
    var totalQuantity : Int? = 0
    lateinit var address : String
    lateinit var email : String
    lateinit var shippingCustomerName : String
    lateinit var number : String
    lateinit var name : String
    private var PAYPAL_REQUEST_CODE = 7171
    var finalTotalPayableAmount : Double ? = 0.0
    lateinit var totalAmountPayable : String

    // DECLARATION FOR STRIPE PAYMENT
    lateinit var paymentSheet: PaymentSheet
    lateinit var customerConfig: PaymentSheet.CustomerConfiguration
    lateinit var paymentIntentClientSecret: String

    private lateinit var mProgressBar : ProgressBar

//    private val paypalConfiguration = PayPalConfiguration()
//        .environment(PayPalConfiguration.ENVIRONMENT_SANDBOX)
//        .clientId(PAYPAL_CLIENT_ID)

    override fun onCreate(savedInstanceState: Bundle?) {

        userManager = UserManager(requireContext())
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
 //       inflater.inflate(R.layout.fragment_checkout, container, false)

        binding = FragmentCheckoutBinding.inflate(layoutInflater)


        paymentSheet = PaymentSheet(this,  ::onPaymentSheetResult)

        mProgressBar = binding.progressBar
        mProgressBar.visibility = View.INVISIBLE

         cartList = requireArguments().getParcelableArrayList<CartModel>("CartProducts")!!
         cartTotal = requireArguments().getDouble("CartTotal")
         cartTotalTax = requireArguments().getDouble("cartTotalTax")
         cartTotalPayable = requireArguments().getDouble("cartTotalPayable")
         totalQuantity = requireArguments().getInt("totalQuantity")

        address = requireArguments().getString("ShippingAddress").toString()
        email = requireArguments().getString("shippingEmail").toString()
        number = requireArguments().getString("ShippingNumber").toString()
        name = requireArguments().getString("shippingCustomerName").toString()

        val adapter = CheckoutProductAdapter(requireContext())
        binding.checkoutCartProducts.adapter = adapter
        binding.checkoutCartProducts.setHasFixedSize(true)
        adapter.submitList(cartList)


        binding.totalPayableAmount.text = String.format("$ %.2f",cartTotalPayable)
        binding.shippingTaxAmount.text = String.format("$ %.2f",cartTotalTax)
        binding.subtotalAmount.text = String.format("$ %.2f",cartTotal)
        binding.shippingCostAmount.text = String.format("$ %.2f",4.99)
        binding.shippingAddress.text = address
        binding.shippingName.text = name
        binding.shippingNumber.text = number
        binding.shippingEmail.text = email

        finalTotalPayableAmount = cartTotalPayable!! + cartTotalTax!! + cartTotal!! + 4.99
        totalAmountPayable =  String.format("$ %.2f",finalTotalPayableAmount)

        binding.totalAmount.text = totalAmountPayable
        binding.totalPayableAmount.text = totalAmountPayable
        binding.discount.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
               Log.d("Discount text field","Discount text field Pressed")
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                binding.enterDiscountCodeButton.background = ContextCompat.getDrawable(context!!,R.drawable.discount_button1)
            }

            override fun afterTextChanged(p0: Editable?) {
                if(p0.isNullOrEmpty()) {
                    binding.enterDiscountCodeButton.background = ContextCompat.getDrawable(context!!,R.drawable.discount_button2)
                }
                else
                {
                    binding.enterDiscountCodeButton.background = ContextCompat.getDrawable(context!!,R.drawable.discount_button1)
                }
            }

        })
        binding.enterDiscountCodeButton.setOnClickListener {
            Snackbar.make(requireView(),"We're currently in development phase",Snackbar.LENGTH_SHORT).show()
        }

        binding.makePayment.setOnClickListener{

            mProgressBar.visibility = View.VISIBLE
            setupPaymentForCustomer()

        }






//        Fuel.post("https://us-central1-agriappcapstoneproject.cloudfunctions.net/agriShop?amt=$finalTotalPayableAmount",null)
//            .responseString(object : com.github.kittinunf.fuel.core.Handler<String>{
//                override fun failure(error: FuelError) {
//
//                }
//
//                override fun success(value: String) {
//                    try {
//                        val result = JSONObject(value)
//
//
//                    }catch (e : JSONException){
//                        Toast.makeText(requireContext(), e.message, Toast.LENGTH_SHORT).show()
//                    }
//
//                }
//
//            })


        // setupPaypal()


       // addDataIntoFirebase()


        return binding.root
    }

    private fun setupPaymentForCustomer() {

        var totalPayable = String.format("%.2f", finalTotalPayableAmount)
        val finalTotalPayable = totalPayable.toDouble() * 100

        val paymentInBigDecimal = BigDecimal(finalTotalPayable).setScale(0, BigDecimal.ROUND_HALF_UP)


        "https://us-central1-agriappcapstoneproject.cloudfunctions.net/agriShop?amt=${paymentInBigDecimal}".httpPost().responseJson { _, _, result ->

            if (result is Result.Success) {
                val responseJson = result.get().obj()
                paymentIntentClientSecret = responseJson.getString("paymentIntent")
                customerConfig = PaymentSheet.CustomerConfiguration(
                    responseJson.getString("customer"),
                    responseJson.getString("ephemeralKey")
                )
                val publishableKey = responseJson.getString("publishableKey")
                PaymentConfiguration.init(requireContext(), publishableKey)

                    showStripePaymentSheet()

            }
        }
    }

    private fun showStripePaymentSheet() {

        paymentSheet.presentWithPaymentIntent(
            paymentIntentClientSecret,
            PaymentSheet.Configuration(
                merchantDisplayName = "Agro Mart",
                customer = customerConfig,
                allowsDelayedPaymentMethods = true
            )
        )
    }

    private fun setupPaypal() {
        //        val intent = Intent(requireContext(),PayPalService::class.java)
//        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION,paypalConfiguration)
//        requireContext().startService(intent)

//        PayPalCheckout.setConfig(CheckoutConfig(
//            application = Application(),
//            clientId = PAYPAL_CLIENT_ID,
//            environment = Environment.SANDBOX,
//            returnUrl = "com.example.mapd726.groupproject.team3.agriapp://paypalpay",
//            currencyCode = CurrencyCode.CAD,
//            userAction = UserAction.PAY_NOW,
//            settingsConfig = SettingsConfig(
//                loggingEnabled = true
//            )
//
//        )
//        )

//        binding.paymentButtonContainer.setup(
//            createOrder =
//            CreateOrder { createOrderActions ->
//                val order =
//                    Order(
//                        intent = OrderIntent.CAPTURE,
//                        appContext = AppContext(userAction = UserAction.PAY_NOW),
//                        purchaseUnitList =
//                        listOf(
//                            PurchaseUnit(
//                                amount =
//                                Amount(currencyCode = CurrencyCode.CAD, value = finalTotalPayableAmount.toString())
//                            )
//                        )
//                    )
//                createOrderActions.create(order)
//            },
//            onApprove =
//            OnApprove { approval ->
//                approval.orderActions.capture { captureOrderResult ->
//                    Log.i("CaptureOrder", "CaptureOrderResult: $captureOrderResult")
//                    Snackbar.make(requireView(),"Payment has been Approved",Snackbar.LENGTH_SHORT).show()
//                    processOrder(captureOrderResult)
//
//                }
//            },
//            onCancel = OnCancel {
//                Log.d("OnCancel", "Buyer canceled the PayPal experience.")
//                Snackbar.make(requireView(),"Payment has been Canceled",Snackbar.LENGTH_SHORT).show()
//            },
//            onError = OnError { errorInfo ->
//                Log.d("OnError", "Error: $errorInfo")
//                Toast.makeText(requireContext(), "Error: $errorInfo", Toast.LENGTH_SHORT).show()
//            }
//        )
//
//

    }



    private fun onPaymentSheetResult(paymentSheetResult: PaymentSheetResult) {

        when(paymentSheetResult) {
            is PaymentSheetResult.Canceled -> {
                mProgressBar.visibility = View.INVISIBLE
                Snackbar.make(requireView(),"Payment is canceled",Snackbar.LENGTH_SHORT).show()
                Log.d("PaymentStatus","Payment is cancelled")
            }
            is PaymentSheetResult.Failed -> {
                mProgressBar.visibility = View.INVISIBLE
                Snackbar.make(requireView(),"Payment is failed Please try again !",Snackbar.LENGTH_SHORT).show()
                Log.d("PaymentStatus","Payment is failed")
            }
            is PaymentSheetResult.Completed -> {
                mProgressBar.visibility = View.INVISIBLE
                Snackbar.make(requireView(),"Thank you for Payment . Please wait while we generate your Order !",Snackbar.LENGTH_LONG).show()
                mProgressBar.visibility = View.VISIBLE
                generateCustomerOrder()
                Log.d("PaymentStatus","Payment is successful")
            }
        }

    }

    private fun generateCustomerOrder() {
        // STORING ORDER DATA INTO ORDERED PRODUCT MODEL
        var i = 0
        for(each in cartList)
        {
            while(i < cartList.size)
            {
                orderedProductsList[i].productId = each.productId
                orderedProductsList[i].productImage = each.productCoverImg
                orderedProductsList[i].orderedProductQuantity = each.productQuantity.toString()
                orderedProductsList[i].productSellingPrice = each.productSpecialPrice
                orderedProductsList[i].orderedProductName = each.productName
            }
            i+=1
        }

        // ADDING DATA INTO FIREBASE
        val firestore = Firebase.firestore.collection("Orders")
        val key = firestore.document().id

        val customerOrderData = OrderModel(
            key,
            userManager.getUserId().toString(),
            "Approved",
            Date(),
            totalAmountPayable,
            "Ordered",
            "",
            address,
            name,
            email,
            userManager.getUserNumber().toString(),
            userManager.getUserName().toString(),
            orderedProductsList
        )
        firestore.add(customerOrderData).addOnSuccessListener {
            Snackbar.make(requireView(),"Your Order Placed Successfully , Please Wait ",Snackbar.LENGTH_SHORT).show()

            // Add this Order into Room DB



        }
            .addOnFailureListener {
                Toast.makeText(requireContext(), "Something went wrong..", Toast.LENGTH_SHORT).show()
            }



        CoroutineScope(Dispatchers.Main).launch {

            delay(2000)
            val navigation = Navigation.findNavController(requireView())
            navigation.clearBackStack(navigation.previousBackStackEntry?.id!!)

            val sender =
                (FragmentComponentManager.findActivity(requireContext()) as Activity as FragmentActivity).supportFragmentManager
            val bundle = Bundle()
            bundle.putParcelableArrayList("OrderItems", cartList)
            bundle.putString("OrderAddress", address)
            bundle.putString("OrderEmail", email)
            bundle.putString("OrderNumber", number)
            bundle.putString("OrderName", name)
            bundle.putString("OrderTotal", totalAmountPayable)

            sender.setFragmentResult("cartData", bundle)
            navigation.navigate(R.id.action_checkoutFragment_to_orderConfirmationFragment,
                bundle)

        }
    }

//    private fun processPayment() {
//        binding.paymentButtonContainer.setup(
//            createOrder =
//            CreateOrder { createOrderActions ->
//                val order =
//                    Order(
//                        intent = OrderIntent.CAPTURE,
//                        appContext = AppContext(userAction = UserAction.PAY_NOW),
//                        purchaseUnitList =
//                        listOf(
//                            PurchaseUnit(
//                                amount =
//                                Amount(currencyCode = CurrencyCode.CAD, value = finalTotalPayableAmount.toString())
//                            )
//                        )
//                    )
//                createOrderActions.create(order)
//            },
//            onApprove =
//            OnApprove { approval ->
//                approval.orderActions.capture { captureOrderResult ->
//                    Log.i("CaptureOrder", "CaptureOrderResult: $captureOrderResult")
//                    Snackbar.make(requireView(),"Payment has been Approved",Snackbar.LENGTH_SHORT).show()
//                    processOrder(captureOrderResult)
//
//                }
//            },
//                    onCancel = OnCancel {
//                Log.d("OnCancel", "Buyer canceled the PayPal experience.")
//                        Snackbar.make(requireView(),"Payment has been Canceled",Snackbar.LENGTH_SHORT).show()
//            },
//            onError = OnError { errorInfo ->
//                Log.d("OnError", "Error: $errorInfo")
//                Toast.makeText(requireContext(), "Error: $errorInfo", Toast.LENGTH_SHORT).show()
//            }
//        )
//
//

//        val paypalPayment = PayPalPayment(BigDecimal.valueOf(finalTotalPayableAmount!!),"CAD","Agro Mart",PayPalPayment.PAYMENT_INTENT_SALE)
//
//        val intent = Intent(requireContext(),PaymentActivity::class.java)
//        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION,paypalConfiguration)
//        intent.putExtra(PaymentActivity.EXTRA_PAYMENT,paypalPayment)
//        startActivityForResult(intent,PAYPAL_REQUEST_CODE)



//    private fun processOrder(captureOrderResult: CaptureOrderResult) {
//
//        var i = 0
//        for(each in cartList)
//        {
//            while(i < cartList.size)
//            {
//                orderedProductsList[i].productId = each.productId
//                orderedProductsList[i].productImage = each.productCoverImg
//                orderedProductsList[i].orderedProductQuantity = each.productQuantity.toString()
//                orderedProductsList[i].productSellingPrice = each.productSpecialPrice
//                orderedProductsList[i].orderedProductName = each.productName
//            }
//            i+=1
//        }
//
//        // ADDING DATA INTO FIREBASE
//        val firestore = Firebase.firestore.collection("Orders")
//        val key = firestore.document().id
//
//        val customerOrderData = OrderModel(
//            key,
//            userManager.getUserId().toString(),
//            captureOrderResult.toString(),
//            Date(),
//            totalAmountPayable,
//            "Ordered",
//            "",
//            address,
//            name,
//            email,
//            userManager.getUserNumber().toString(),
//            userManager.getUserName().toString(),
//            orderedProductsList
//        )
//        firestore.add(customerOrderData).addOnSuccessListener {
//            Snackbar.make(requireView(),"Order Placed Successfully",Snackbar.LENGTH_SHORT).show()
//
//            // Add this Order into Room DB
//
//
//
//        }
//            .addOnFailureListener {
//                Toast.makeText(requireContext(), "Something went wrong..", Toast.LENGTH_SHORT).show()
//            }
//
//
//
//        CoroutineScope(Dispatchers.Main).launch {
//
//            delay(2000)
//            val navigation = Navigation.findNavController(requireView())
//            navigation.clearBackStack(navigation.previousBackStackEntry?.id!!)
//
//            val sender =
//                (FragmentComponentManager.findActivity(requireContext()) as Activity as FragmentActivity).supportFragmentManager
//            val bundle = Bundle()
//            bundle.putParcelableArrayList("OrderItems", cartList)
//            bundle.putString("OrderAddress", address)
//            bundle.putString("OrderEmail", email)
//            bundle.putString("OrderNumber", number)
//            bundle.putString("OrderName", name)
//            bundle.putString("OrderTotal", totalAmountPayable)
//            bundle.putString("paymentDetails", captureOrderResult.toString())
//
//            sender.setFragmentResult("cartData", bundle)
//            navigation.navigate(R.id.action_checkoutFragment_to_orderConfirmationFragment,
//                bundle)
//
//    }
//
//    }

//    override fun onDestroy() {
//       // requireContext().stopService(Intent(requireContext(),PayPalService::class.java))
//        super.onDestroy()
//    }
//
//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        if(requestCode == PAYPAL_REQUEST_CODE){
//            if(requestCode == RESULT_OK)
//            {


        //    val paymentConfirmation : PaymentConfirmation? = data?.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION)
         //       if(paymentConfirmation!=null)
          //      {
           //         try{
            //            val paymentDetails = paymentConfirmation.toJSONObject().toString(4)

                        // Store new Order to firebase


//                        // STORING ORDER DATA INTO ORDERED PRODUCT MODEL
//                        var i = 0
//                        for(each in cartList)
//                        {
//                            while(i < cartList.size)
//                        {
//                            orderedProductsList[i].productId = each.productId
//                            orderedProductsList[i].productImage = each.productCoverImg
//                            orderedProductsList[i].orderedProductQuantity = each.productQuantity.toString()
//                            orderedProductsList[i].productSellingPrice = each.productSpecialPrice
//                            orderedProductsList[i].orderedProductName = each.productName
//                        }
//                            i+=1
//                        }

//                        // ADDING DATA INTO FIREBASE
//                        val firestore = Firebase.firestore.collection("Orders")
//                        val key = firestore.document().id
//
//                        val customerOrderData = OrderModel(
//                            key,
//                            userManager.getUserId().toString(),
//                            "Approved",
//                            Date(),
//                            totalAmountPayable,
//                            "Ordered",
//                            "",
//                            address,
////                            name,
////                            email,
//                            userManager.getUserNumber().toString(),
//                            userManager.getUserName().toString(),
//                            orderedProductsList
//                        )
//                        firestore.add(customerOrderData).addOnSuccessListener {
//                            Snackbar.make(requireView(),"Order Placed Successfully",Snackbar.LENGTH_SHORT).show()
//
//                            // Add this Order into Room DB


//
//                        }
//                            .addOnFailureListener {
//                                Toast.makeText(requireContext(), "Something went wrong..", Toast.LENGTH_SHORT).show()
//                            }

//
//
//                        CoroutineScope(Dispatchers.Main).launch {
//
//                            delay(2000)
//                            val navigation = Navigation.findNavController(requireView())
//                            navigation.clearBackStack(navigation.previousBackStackEntry?.id!!)
//
//                            val sender =
//                                (FragmentComponentManager.findActivity(requireContext()) as Activity as FragmentActivity).supportFragmentManager
//                            val bundle = Bundle()
//                            bundle.putParcelableArrayList("OrderItems", cartList)
//                            bundle.putString("OrderAddress", address)
//                            bundle.putString("OrderEmail", email)
//                            bundle.putString("OrderNumber", number)
//                            bundle.putString("OrderName", name)
//                            bundle.putString("OrderTotal", totalAmountPayable)
//                            bundle.putString("paymentDetails", paymentDetails)
//
//                            sender.setFragmentResult("cartData", bundle)
//                            navigation.navigate(R.id.action_checkoutFragment_to_orderConfirmationFragment,
//                                bundle)
//
//                        }
                //    }catch (e : JSONException){
              //          e.printStackTrace()
                 //   }
               // } else if (requestCode == RESULT_CANCELED)
               // {
               //     Toast.makeText(requireContext(), "Cancel", Toast.LENGTH_SHORT).show()
               // }

         //   }else if(requestCode == PaymentActivity.RESULT_EXTRAS_INVALID)
         //   {
          //      Toast.makeText(requireContext(), "Invalid Payment", Toast.LENGTH_SHORT).show()
           // }
        //}

}

