import random
import ast


class blue_slime():
    def __init__(self):
        self.stats = dict({'NAME': 'Blue Slime', 'HP': random.randint(1,11), 'ATK': random.randint(1,5), 'DEF': random.randint(1,2), 'STATUS': 'ALIVE'})
    
    def return_name(self):
        return self.stats['NAME']
    
    def return_hp(self):
        return self.stats['HP']
    
    def return_atk(self):
        return self.stats['ATK']
    
    def return_defense(self):
        return self.stats['DEF']
    
    def lose_hp(self, atk):
        dmg = atk - self.return_defense()
        
        if dmg > 0:
            self.stats['HP'] = self.return_hp() - dmg
            
            if self.stats['HP'] <= 0:
                self.stats['HP'] = 0
                self.stats['STATUS'] = 'DEAD'
                
        return self
    
    def is_alive(self):
        if self.stats['STATUS'] == 'ALIVE':
            return True
        else:
            return False
    
def show_enemy_stat(battle):
    print("Enemies: ")
    for idx, mon in enumerate(battle):
        HP = mon.return_hp()
        mon_name = mon.return_name()
        print(mon_name + " " + str(idx + 1) + " HP: " + str(HP), end= "")
        
        if mon.is_alive() is False:
            print(" -> Defeated")
        else:
            print("")
