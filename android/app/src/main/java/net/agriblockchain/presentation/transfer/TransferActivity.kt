package net.agriblockchain.presentation.transfer

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.widget.TableLayout
import android.widget.TextView
import android.widget.Toast

import net.agriblockchain.R
import net.agriblockchain.data.model.Product
import net.agriblockchain.data.model.Request
import net.agriblockchain.util.DateFormatter

class TransferActivity : AppCompatActivity(), TransferContract.View {
    private lateinit var tblOwnedProducts: TableLayout

    val presenter: TransferContract.Presenter = TransferPresenter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_transfer)

        tblOwnedProducts = findViewById(R.id.tbl_owned_products)

        presenter.getOwnedProducts(intent.getStringExtra("stakeholderId"))
        presenter.getSubmittedRequests(intent.getStringExtra("stakeholderId"))
        presenter.getReceivedRequests(intent.getStringExtra("stakeholderId"))
    }

    override fun transferSuccess() {

    }

    override fun onFailure(errorMsg: String) {
        Toast.makeText(this, errorMsg, Toast.LENGTH_LONG).show()
    }

    override fun onSubmittedRequests(requests: List<Request>) {
        //todo
    }

    override fun onReceivedRequests(requests: List<Request>) {
        // todo
    }

    override fun onOwnedProducts(products: List<Product>) {
        val layoutInflater: LayoutInflater = layoutInflater
        for (product in products) {
            val view: View = layoutInflater.inflate(R.layout.table_row_4, tblOwnedProducts)

            val txtProductId: TextView = view.findViewById(R.id.txt_col_1)
            val txtPluckedDate: TextView = view.findViewById(R.id.txt_col_2)
            val txtProductType: TextView = view.findViewById(R.id.txt_col_3)
            val txtQuantity: TextView = view.findViewById(R.id.txt_col_4)

            txtProductId.text = product.productId
            txtPluckedDate.text = DateFormatter.getFormattedDate(product.pluckedDate)
            txtProductType.text = product.productType
            txtQuantity.text = product.quantity.toString()

            tblOwnedProducts.addView(view)
        }
    }

}
