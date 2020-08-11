package co.tiagoaguiar.evernotekt.add.presentation

import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.MenuItem
import android.widget.Toast
import androidx.annotation.DrawableRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import co.tiagoaguiar.evernotekt.R
import co.tiagoaguiar.evernotekt.add.Add
import co.tiagoaguiar.evernotekt.model.Note
import co.tiagoaguiar.evernotekt.model.RemoteDataSource
import kotlinx.android.synthetic.main.activity_form.*
import kotlinx.android.synthetic.main.content_form.*


class FormActivity : AppCompatActivity(),
    TextWatcher,
    Add.View {

    private lateinit var presenter: Presenter

    private var toSave: Boolean = false
    private var noteId: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form)

        noteId = intent.extras?.getInt("noteId")

        setupPresenter()
        setupViews()
    }

    override fun onStart() {
        super.onStart()
        noteId?.let {
            presenter.getNote(it)
        }
    }

    override fun onStop() {
        super.onStop()
        presenter.stop()
    }

    private fun setupPresenter() {
        val dataSource = RemoteDataSource()
        presenter = Presenter(this, dataSource)
    }

    private fun setupViews() {
        setSupportActionBar(toolbar)
        toggleToolbar(R.drawable.ic_arrow_back_black_24dp)

        note_title.addTextChangedListener(this)
        note_editor.addTextChangedListener(this)
    }

    private fun toggleToolbar(@DrawableRes icon: Int) {
        supportActionBar?.let {
            it.title = null
            val upArrow = ContextCompat.getDrawable(this, icon)
            val colorFilter =
                PorterDuffColorFilter(
                    ContextCompat.getColor(
                        this,
                        R.color.colorAccent
                    ),
                    PorterDuff.Mode.SRC_ATOP
                )
            upArrow?.colorFilter = colorFilter
            it.setHomeAsUpIndicator(upArrow)
            it.setDisplayHomeAsUpEnabled(true)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            return if (toSave && noteId == null) {
                presenter.createNote(
                    note_title.text.toString(),
                    note_editor.text.toString()
                )

                true
            } else {
                finish()
                true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
    }

    override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
        toSave =
            if (note_editor.text.toString().isEmpty() && note_title.text.toString().isEmpty()) {
                toggleToolbar(R.drawable.ic_arrow_back_black_24dp)
                false
            } else {
                toggleToolbar(R.drawable.ic_done_black_24dp)
                true
            }
    }

    override fun afterTextChanged(editable: Editable) {
    }

    override fun returnToHome() {
        finish()
    }

    override fun displayError(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    override fun displayNote(title: String, body: String) {
        note_title.setText(title)
        note_editor.setText(body)
    }


}