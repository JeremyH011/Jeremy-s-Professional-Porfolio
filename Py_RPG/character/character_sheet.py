import random
import ast

class character():
    def __init__(self, load):
        
        if load is not True:
            print("\nCreating new character...")
            self.stats = dict({'LEVEL': 1, 'HP': random.randint(10,21), 'ATK': random.randint(5,11), 'DEF': random.randint(5,11)})
        else:
            sheet = open("save.txt", 'r')
            self.stats = ast.literal_eval(sheet.readline())
            
    def reroll(self):
        self.stats = dict({'LEVEL': 1, 'HP': random.randint(10,21), 'ATK': random.randint(5,11), 'DEF': random.randint(5,11)})
        self.display_stats()
    
    def increase_stat(self, stat, value):
        self.stats[str(stat)] += value
    
    def display_stats(self):
        print("LEVEL: " + str(self.stats['LEVEL']) + " HP: " + str(self.stats['HP']) + " ATK: " + str(self.stats['ATK']) + " DEF: " + str(self.stats['DEF']))
    
    def save_char(self):
        sheet = open("save.txt", "w+")
        sheet.write(str(self.stats))
        sheet.close()
        
    def level_up(self):
        
        level = self.stats['LEVEL']
        increase_health = random.randint(0,3)
        increase_atk = random.randint(0,3)
        increase_def = random.randint(0,3)

        lvl_p = 'LEVEL: {:d} \u2191 {:d}'.format(level, level+1)
        print(lvl_p)
        
        hp_p = 'HP: {:d}'.format(self.stats['HP']) 
        if increase_health > 0:
            hp_p = hp_p + ' \u2191 {:d}'.format(self.stats['HP']+increase_health)
        print(hp_p)

        atk_p = 'ATK: {:d}'.format(self.stats['ATK']) 
        if increase_atk > 0:
            atk_p = atk_p + ' \u2191 {:d}'.format(self.stats['ATK']+increase_atk)
        print(atk_p)

        def_p = 'DEF: {:d}'.format(self.stats['DEF']) 
        if increase_def > 0:
            def_p = def_p + ' \u2191 {:d}'.format(self.stats['DEF']+increase_def)
        print(def_p)

        self.stats['LEVEL'] += 1
        self.stats['HP'] += increase_health
        self.stats['ATK'] += increase_atk
        self.stats['DEF'] += increase_def

#Character creation
def character_create(character_1, load):
    if load is not True:
        character_1 = character(False)
        character_1.display_stats()
        choice = '1'
        while choice == '1':
            choice = input("Would you like to reroll character? (Press 1 for Y) ")
            print("")
            if(choice == '1'):
                character_1.reroll()
            else:
                break;
    else:
        character_1 = character(True)
    
    print("This is your character: ")
    character_1.display_stats()
    
    return character_1
