package pokemon.planner.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import pokemon.planner.R

class TeamFragment: Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
        ): View? {
        val view: View = inflater.inflate(R.layout.teamviewer_team_fragment, container, false)
        var nameText = view.findViewById<TextView>(R.id.nameLabel)
        return view
    }
}