package com.example.langbuddy

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateInterpolator
import android.view.animation.LinearInterpolator
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DefaultItemAnimator
import com.yuyakaido.android.cardstackview.*
import io.ktor.client.HttpClient
import io.ktor.client.features.json.GsonSerializer
import io.ktor.client.features.json.JsonFeature
import kotlinx.android.synthetic.main.fragment_swipe.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class SwipeFragment : Fragment(), CardStackListener, View.OnClickListener {
    private val manager by lazy { CardStackLayoutManager(activity!!.applicationContext, this) }
    private var cardStackView: CardStackView? = null
    private val adapter by lazy { CardStackAdapter(emptyList(), this) }
    private var currentUser: User? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_swipe, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        GlobalScope.launch {
            val client = HttpClient() {
                install(JsonFeature) {
                    serializer = GsonSerializer {
                        // .GsonBuilder
                        serializeNulls()
                        disableHtmlEscaping()
                    }
                }
            }

//            val response = client.get<String>("http://10.0.2.2:3000/products")
//            val gson = GsonBuilder().serializeNulls().create()
//            var users = gson.fromJson<Products>(response, Products::class.java)
            //adapter.setProducts(users.users)
            val testProducts = mutableListOf<User>()
            testProducts.add(User(
                    1321684,
                    "Dominik Grüneis",
                    "GER ENG",
                    "asdf"
                )
            )
            adapter.setProducts(testProducts)
            client.close()
            activity!!.runOnUiThread {
                setupCardStackView()
                setupButton()
            }
        }
    }


    private fun setupCardStackView() {
        initialize()
    }

    private fun initialize() {
        manager.setStackFrom(StackFrom.None)
        manager.setVisibleCount(3)
        manager.setTranslationInterval(8.0f)
        manager.setScaleInterval(0.95f)
        manager.setSwipeThreshold(0.1f)
        manager.setMaxDegree(20.0f)
        manager.setDirections(Direction.HORIZONTAL)
        manager.setCanScrollHorizontal(true)
        manager.setCanScrollVertical(true)
        manager.setSwipeableMethod(SwipeableMethod.AutomaticAndManual)
        manager.setOverlayInterpolator(LinearInterpolator())
        cardStackView = card_stack_view
        cardStackView!!.layoutManager = manager
        cardStackView!!.adapter = adapter
        cardStackView!!.itemAnimator.apply {
            if (activity is DefaultItemAnimator) {
                val a = activity as DefaultItemAnimator
                a.supportsChangeAnimations = false;
            }
        }
    }

    override fun onCardDisappeared(view: View?, position: Int) {
    }

    override fun onCardDragging(direction: Direction?, ratio: Float) {
    }

    override fun onCardSwiped(direction: Direction?) {
        when (direction) {
            Direction.Right -> {
                println("Try to match")
            }
            Direction.Left -> {
                println("Throw it away")
            }
            else -> {
                println("Error. Invalid swipe direction")
            }
        }
    }

    override fun onCardCanceled() {
    }

    override fun onCardAppeared(view: View?, position: Int) {
        currentUser = adapter.getProducts()[position]
    }

    override fun onCardRewound() {
    }

    private fun setupButton() {
        val declineButton = view!!.findViewById<View>(R.id.decline_button)
        declineButton.setOnClickListener {
            val setting = SwipeAnimationSetting.Builder()
                .setDirection(Direction.Left)
                .setDuration(Duration.Normal.duration)
                .setInterpolator(AccelerateInterpolator())
                .build()
            manager.setSwipeAnimationSetting(setting)
            cardStackView!!.swipe()
        }

        val bidButton = view!!.findViewById<View>(R.id.bid_button)
        bidButton.setOnClickListener {
            val setting = SwipeAnimationSetting.Builder()
                .setDirection(Direction.Right)
                .setDuration(Duration.Normal.duration)
                .setInterpolator(AccelerateInterpolator())
                .build()
            manager.setSwipeAnimationSetting(setting)
            cardStackView!!.swipe()
        }
    }

    override fun onClick(v: View?) {
        val a = activity as MainActivity
        println("Heeeeeeeeeeeeeeeeeeeeeeeeereeeeeeeeeeeee")
        a.showProductDetailFragment(currentUser!!)
    }
}
