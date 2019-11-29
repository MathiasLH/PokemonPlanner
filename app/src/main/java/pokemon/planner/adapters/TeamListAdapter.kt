package pokemon.planner.adapters

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import pokemon.planner.R
import pokemon.planner.model.GameVersion
import pokemon.planner.model.Team

class TeamListAdapter(private val context: Context, private val teams: ArrayList<Team>) : RecyclerView.Adapter<TeamListAdapter.ViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var vh = ViewHolder(LayoutInflater.from(context).inflate(R.layout.team_selector_item, parent, false))
        //do stuff
        return vh
    }

    override fun getItemCount(): Int {
        return teams.size
    }

    //ugly af
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.nameText.text = teams.get(position).name
        holder.versionLabel.text = teams.get(position).version.readableName
        holder.rect1.setBackgroundColor(Color.parseColor(teams.get(position).version.color))
        holder.rect2.setBackgroundColor(Color.parseColor(teams.get(position).version.color))
        holder.rect3.setBackgroundColor(Color.parseColor(teams.get(position).version.color))
        var version = teams.get(position).version
        if(version.equals(GameVersion.RED) || version.equals(GameVersion.BLUE)){
            holder.image.setImageResource(R.drawable.red_blue)
        }else if(version.equals(GameVersion.YELLOW)){
            holder.image.setImageResource(R.drawable.yellow)
        }else if(version.equals(GameVersion.GOLD) || version.equals(GameVersion.SILVER)){
            holder.image.setImageResource(R.drawable.gold_silver)
        }else if(version.equals(GameVersion.CRYSTAL)){
            if(teams.get(position).gender){
                holder.image.setImageResource(R.drawable.crystal_male)
            }else{
                holder.image.setImageResource(R.drawable.crystal_female)
            }
        }else if(version.equals(GameVersion.RUBY) || version.equals(GameVersion.SAPPHIRE)){
            if(teams.get(position).gender){
                holder.image.setImageResource(R.drawable.ruby_sapphire_male)
            }else{
                holder.image.setImageResource(R.drawable.ruby_sapphire_female)
            }
        }else if(version.equals(GameVersion.EMERALD)){
            if(teams.get(position).gender){
                holder.image.setImageResource(R.drawable.emerald_male)
            }else{
                holder.image.setImageResource(R.drawable.emerald_female)
            }
        }else if(version.equals(GameVersion.FIRERED) || version.equals(GameVersion.LEAFGREEN)){
            if(teams.get(position).gender){
                holder.image.setImageResource(R.drawable.firered_leafgreen_male)
            }else{
                holder.image.setImageResource(R.drawable.firered_leafgreen_female)
            }
        }else if(version.equals(GameVersion.DIAMOND) || version.equals(GameVersion.PEARL)){
            if(teams.get(position).gender){
                holder.image.setImageResource(R.drawable.diamond_pearl_male)
            }else{
                holder.image.setImageResource(R.drawable.diamond_pearl_female)
            }
        }else if(version.equals(GameVersion.PLATINUM)){
            if(teams.get(position).gender){
                holder.image.setImageResource(R.drawable.platinum_male)
            }else{
                holder.image.setImageResource(R.drawable.platinum_female)
            }
        }else if(version.equals(GameVersion.HEARTGOLD) || version.equals(GameVersion.SOULSILVER)){
            if(teams.get(position).gender){
                holder.image.setImageResource(R.drawable.heartgold_soulsilver_male)
            }else{
                holder.image.setImageResource(R.drawable.heartgold_soulsilver_female)
            }
        }else if(version.equals(GameVersion.BLACK) || version.equals(GameVersion.WHITE)){
            if(teams.get(position).gender){
                holder.image.setImageResource(R.drawable.black_white_male)
            }else{
                holder.image.setImageResource(R.drawable.black_white_female)
            }
        }else if(version.equals(GameVersion.BLACK2) || version.equals(GameVersion.WHITE2)){
            if(teams.get(position).gender){
                holder.image.setImageResource(R.drawable.black2_white2_male)
            }else{
                holder.image.setImageResource(R.drawable.black2_white2_female)
            }
        }
    }


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nameText = view.findViewById<TextView>(R.id.teamName)
        val image = view.findViewById<ImageView>(R.id.playerImage)
        val versionLabel = view.findViewById<TextView>(R.id.versionLabel)
        val rect1 = view.findViewById<View>(R.id.rect1)
        val rect2 = view.findViewById<View>(R.id.rect2)
        val rect3 = view.findViewById<View>(R.id.rect3)
    }

}